package com.jagex.game.runetek5.client.hd;

import java.util.EnumMap;

import com.jagex.game.runetek5.client.config.LaunchConfig;

/**
 * The 592HD settings state holder.
 *
 * <p>Shaped like the client's other preferences ({@code CameraZoom},
 * {@code FogDistance}, {@code Tweening}): a single owner for the value, a
 * preset table, a save/load pair, and a package-private test hook. The pane,
 * the console commands and the persistence layer all go through here, so the
 * three can never disagree.
 *
 * <p>Two deliberate rules:
 *
 * <ul>
 * <li><b>Off writes nothing.</b> Selecting {@link HdPreset#OFF} disables the
 * feature set and leaves every stock graphics flag alone, so a player who
 * turns 592HD off gets exactly the client they had before.</li>
 * <li><b>First-run defaults wait for the pipeline.</b> The shipped default is
 * Medium, but its READY rows are not pushed into the running client until the
 * HD pipeline exists ({@link HdCapabilities#lightingAvailable()}). This
 * keeps the foundation slice inert: the choice is stored and shown, and the
 * stock renderer is untouched until there is an HD path to apply it to.</li>
 * </ul>
 */
public final class HdSettings {

	private static final EnumMap<HdFeature, Integer> values = new EnumMap<HdFeature, Integer>(HdFeature.class);

	private static boolean enabled;

	private static HdPreset preset = HdPreset.CUSTOM;

	private static boolean firstRunApplied;

	/** True once this session has re-applied stored deviations from stock. */
	private static boolean savedApplied;

	static {
		resetToDefaultPreset();
	}

	private HdSettings() {
	}

	/**
	 * Loads the remembered settings, creating the file at the shipped default
	 * on first run. Never touches the stock renderer; see
	 * {@link #applyPendingDefaults(HdGraphicsBackend)}.
	 */
	public static void load() {
		resetToDefaultPreset();
		if (!HdPreferences.load()) {
			// First run: store the shipped preset so the pane and a later
			// milestone both start from the same saved state.
			HdPreferences.save();
		}
		HdPreset launchPreset = LaunchConfig.hdPreset();
		if (launchPreset != null) {
			applyLaunchPreset(launchPreset);
		}
	}

	/**
	 * Adopts the launcher's preset for this session without writing
	 * {@code hd.properties}. The player's in-game file stays theirs until they
	 * change a row in the pane; only then does {@link #save()} persist the
	 * launch choice along with it.
	 *
	 * <p>Marking the first run applied is what keeps that promise:
	 * {@link #applyPendingDefaults} would otherwise save the preset over the
	 * player's file. The rows that differ from the shipped preset are pushed by
	 * {@link #applySavedDeviations} instead, which never writes the file.
	 */
	private static void applyLaunchPreset(HdPreset selected) {
		for (HdFeature feature : HdFeature.values()) {
			values.put(feature, selected.value(feature));
		}
		enabled = selected.enabled();
		preset = selected;
		firstRunApplied = true;
		savedApplied = false;
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static HdPreset preset() {
		return preset;
	}

	public static boolean isFirstRunApplied() {
		return firstRunApplied;
	}

	public static int value(HdFeature feature) {
		Integer value = values.get(feature);
		return value == null ? feature.defaultValue() : value.intValue();
	}

	public static String valueLabel(HdFeature feature) {
		return feature.valueLabel(value(feature));
	}

	/**
	 * Turns 592HD on or off. Turning it off leaves the stock graphics flags
	 * exactly as they are, which is what makes Off a true "stock" switch.
	 */
	public static void setEnabled(boolean value, HdGraphicsBackend backend) {
		enabled = value;
		if (!value) {
			preset = HdPreset.OFF;
		} else if (preset == HdPreset.OFF) {
			preset = HdPreset.CUSTOM;
		}
		save();
		if (value) {
			// Re-enabling restores the rows the player had chosen; at the
			// shipped preset these all equal stock, so it changes nothing.
			applyReadyRows(backend);
		}
	}

	public static void toggleEnabled(HdGraphicsBackend backend) {
		setEnabled(!enabled, backend);
	}

	/**
	 * Applies a preset's values and, when the preset is enabled, pushes its
	 * READY rows through the backend. Pending rows are stored for the pipeline
	 * and are not applied here.
	 */
	public static void applyPreset(HdPreset selected, HdGraphicsBackend backend) {
		if (selected == null || selected == HdPreset.CUSTOM) {
			return;
		}
		for (HdFeature feature : HdFeature.values()) {
			values.put(feature, selected.value(feature));
		}
		enabled = selected.enabled();
		preset = selected;
		save();
		if (enabled) {
			applyReadyRows(backend);
		}
	}

	/**
	 * Advances a row to its next value and marks the settings custom. Rows the
	 * client cannot support are left alone.
	 *
	 * @return the value now in effect
	 */
	public static int cycle(HdFeature feature, HdGraphicsBackend backend) {
		if (feature == null || !feature.support().selectable()) {
			return feature == null ? 0 : value(feature);
		}
		int next = (value(feature) + 1) % feature.valueCount();
		values.put(feature, next);
		preset = HdPreset.CUSTOM;
		enabled = true;
		save();
		if (feature.support() == HdSupport.READY && feature.appliesToStock()) {
			backend.apply(feature, next);
		}
		return next;
	}

	/**
	 * Writes a row directly. The settings become custom, because a hand-set
	 * row no longer matches any named preset.
	 */
	public static void set(HdFeature feature, int value) {
		int clamped = value < 0 ? 0 : (value >= feature.valueCount() ? feature.valueCount() - 1 : value);
		values.put(feature, clamped);
		preset = HdPreset.CUSTOM;
	}

	/**
	 * Re-applies stored READY rows that differ from the shipped preset, once
	 * per session.
	 *
	 * <p>A plain stock install stores exactly the shipped preset, so this
	 * writes nothing and stock rendering stays bit-identical. A player who
	 * changed a row gets that choice back on the next launch, instead of the
	 * file silently disagreeing with the running client. Rows owned by the HD
	 * pipeline are never touched here; they wait for
	 * {@link #applyPendingDefaults(HdGraphicsBackend)}.
	 *
	 * <p>Called from the frame path because it retries until the client has
	 * created its graphics settings object.
	 */
	public static void applySavedDeviations(HdGraphicsBackend backend) {
		if (savedApplied || !enabled || backend == null || !backend.canApply()) {
			return;
		}
		HdPreset shipped = HdPreset.defaultPreset();
		boolean wrote = false;
		for (HdFeature feature : HdFeature.values()) {
			if (feature.support() != HdSupport.READY || !feature.appliesToStock()) {
				continue;
			}
			int value = value(feature);
			if (value != shipped.value(feature)) {
				backend.apply(feature, value);
				wrote = true;
			}
		}
		if (wrote) {
			backend.flush();
		}
		savedApplied = true;
	}

	/**
	 * Pushes the pending first-run defaults once the pipeline can honour them.
	 * Until then the call is a no-op and the choices stay stored.
	 */
	public static void applyPendingDefaults(HdGraphicsBackend backend) {
		if (firstRunApplied || !enabled || !HdCapabilities.lightingAvailable()) {
			return;
		}
		applyReadyRows(backend);
		firstRunApplied = true;
		save();
	}

	/** Restores the shipped default (Medium) and stores it. */
	public static void reset() {
		resetToDefaultPreset();
		firstRunApplied = false;
		savedApplied = false;
		save();
	}

	/** Persists the current values. Safe to call on every change. */
	public static void save() {
		HdPreferences.save();
	}

	private static void applyReadyRows(HdGraphicsBackend backend) {
		if (backend == null || !backend.canApply()) {
			return;
		}
		for (HdFeature feature : HdFeature.values()) {
			if (feature.support() == HdSupport.READY && feature.appliesToStock()) {
				backend.apply(feature, value(feature));
			}
		}
		backend.flush();
	}

	private static void resetToDefaultPreset() {
		HdPreset shipped = HdPreset.defaultPreset();
		for (HdFeature feature : HdFeature.values()) {
			values.put(feature, shipped.value(feature));
		}
		enabled = shipped.enabled();
		preset = shipped;
		firstRunApplied = false;
		savedApplied = false;
	}

	// -- Load hooks -------------------------------------------------------

	static void setValueForLoad(HdFeature feature, int value) {
		values.put(feature, value);
	}

	static void setEnabledForLoad(boolean value) {
		enabled = value;
	}

	static void setPresetForLoad(HdPreset value) {
		preset = value == null ? HdPreset.CUSTOM : value;
	}

	static void setFirstRunAppliedForLoad(boolean value) {
		firstRunApplied = value;
		savedApplied = false;
	}

	// -- Test hooks -------------------------------------------------------

	/** Test hook: redirects the properties file under the given directory. */
	public static void setDirectoryForTest(java.io.File directory) {
		HdPreferences.setDirectoryForTest(directory);
	}

	/** Test hook: forgets the redirect and restores the shipped preset. */
	public static void resetForTest() {
		HdPreferences.setDirectoryForTest(null);
		resetToDefaultPreset();
	}

	static int presetValueForTest(HdPreset selected, HdFeature feature) {
		return selected.value(feature);
	}
}
