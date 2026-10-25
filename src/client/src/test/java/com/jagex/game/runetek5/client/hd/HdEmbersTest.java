package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.jagex.game.runetek5.client.hd.options.HdOptionsAction;
import com.jagex.game.runetek5.client.hd.options.HdOptionsModel;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Why the embers row is inert, pinned as the data facts that make it so.
 *
 * <p>The row names a look - drifting embers and sparks - that would have to be
 * bound to the client's own particle systems to be honest. This client's
 * particle data cannot name one: a producer is a bag of numbers (colour ramp,
 * sizes, speeds, angles, lifetime, material id), an emitter is a producer id and
 * a priority, and a particle type classifies only how the particle is handled.
 * Nothing distinguishes a fire's embers from the smoke above it; that lives in
 * the artwork a producer id points at.
 *
 * <p>These tests state that as the structural contract, so a later change
 * cannot quietly turn the row on by inferring "embers" from warm colours or
 * upward motion - which would classify art rather than read the client's data.
 * If the cache ever carries a producer-level kind, these tests are where the
 * assumption is re-examined.
 */
@DisplayName("592HD embers row")
class HdEmbersTest {

	@Test
	@DisplayName("the row is marked unsupported and cannot be selected")
	void embersRow_shouldBeUnsupported() {
		assertAll(
			() -> assertEquals(HdSupport.UNAVAILABLE, HdFeature.EMBERS.support()),
			() -> assertFalse(HdFeature.EMBERS.support().selectable(),
				"a row with nothing to bind to must not be offered"),
			() -> assertFalse(HdFeature.EMBERS.support().appliesToday()),
			() -> assertFalse(HdFeature.EMBERS.appliesToStock(),
				"no stock flag carries it either"),
			() -> assertEquals(HdStockTarget.RENDERER, HdFeature.EMBERS.target()),
			// Cycling a row the client cannot support leaves the stored value where
			// it was, so a stray key press cannot turn anything on.
			() -> assertEquals(0, HdSettings.cycle(HdFeature.EMBERS, null)),
			() -> assertEquals(0, HdSettings.value(HdFeature.EMBERS),
				"the value never changes")
		);
	}

	@Test
	@DisplayName("a producer's data is numbers only, so it cannot say what its particles are")
	void particleProducer_shouldCarryOnlyNumbers() {
		int fields = 0;
		for (Field field : ParticleProducerType.class.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			fields++;
			Class<?> type = field.getType();
			assertTrue(type.isPrimitive() || type.isArray() && type.getComponentType().isPrimitive(),
				ParticleProducerType.class.getSimpleName() + "." + field.getName() + " is a "
					+ type.getSimpleName() + ": a producer could then carry a name or a kind");
		}
		assertTrue(fields > 40, "the producer model was not reflected: " + fields + " fields");
	}

	@Test
	@DisplayName("a particle type classifies handling, never appearance")
	void particleType_shouldClassifyOnlyHandling() throws Exception {
		for (Field field : ParticleType.class.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			Class<?> type = field.getType();
			assertTrue(type.isPrimitive() || type.isArray() && type.getComponentType().isPrimitive(),
				ParticleType.class.getSimpleName() + "." + field.getName() + " is a "
					+ type.getSimpleName() + ": a particle type could then name a material");
		}
		// The one classifying field is an int whose values say how a particle is
		// driven (1 = attached to the model's emitter face), not what it is.
		assertEquals(int.class, ParticleType.class.getDeclaredField("particleHandlingType").getType());
	}

	@Test
	@DisplayName("an emitter names a producer id, and nothing else about it")
	void emissiveTriangle_shouldNameOnlyAProducerId() throws Exception {
		Field id = EmissiveTriangle.class.getDeclaredField("id");
		assertAll(
			() -> assertEquals(int.class, id.getType(), "the emitter holds an id, not a kind"),
			() -> assertTrue(Modifier.isPrivate(id.getModifiers())),
			() -> assertEquals(ParticleProducerType.class,
				EmissiveTriangle.class.getMethod("method695").getReturnType(),
				"and the producer is looked up by that id"),
			() -> assertFalse(hasFieldOfType(EmissiveTriangle.class, ParticleProducerType.class),
				"an emitter keeps no classification of its own")
		);
	}

	@Test
	@DisplayName("the pane says the row is unsupported rather than offering it")
	void pane_shouldMarkTheRowUnsupported() {
		HdOptionsModel model = new HdOptionsModel();
		model.apply(HdOptionsAction.tab(HdTab.EFFECTS.ordinal()), new RecordingHdGraphicsBackend());
		int row = -1;
		for (int index = 0; index < model.rowCount(); index++) {
			if (model.rows()[index] == HdFeature.EMBERS) {
				row = index;
			}
		}
		int found = row;
		assertAll(
			() -> assertTrue(found >= 0, "embers is on the Effects tab"),
			() -> assertEquals(HdSupport.UNAVAILABLE, model.rowSupport(found)),
			() -> assertEquals(HdSettings.valueLabel(HdFeature.EMBERS) + " "
				+ HdSupport.UNAVAILABLE.marker(), model.rowValueText(found),
				"the value cell must not read as a live On")
		);
	}

	@Test
	@DisplayName("the row never travels as a draw decision")
	void embersRow_shouldNotTravelAsADrawRow() {
		for (HdDrawRows.Row row : HdDrawRows.Row.values()) {
			assertNotEquals("EMBERS", row.name(),
				"a row with no renderer must not be a Row the draw reads");
		}
		assertFalse(HdFeature.EMBERS.support().appliesToday());
	}

	private static boolean hasFieldOfType(Class<?> owner, Class<?> type) {
		for (Field field : owner.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && field.getType() == type) {
				return true;
			}
		}
		return false;
	}
}
