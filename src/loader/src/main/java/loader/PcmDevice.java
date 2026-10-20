package loader;

import java.awt.Component;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("loader!qn")
public interface PcmDevice {

	@OriginalMember(owner = "loader!qn", name = "a", descriptor = "(Ljava/awt/Component;ZZI)V")
	void method5740(@OriginalArg(0) Component arg0) throws Exception;

	@OriginalMember(owner = "loader!qn", name = "c", descriptor = "(II)I")
	int position();

	@OriginalMember(owner = "loader!qn", name = "a", descriptor = "(BII)V")
	void open() throws Exception;

	@OriginalMember(owner = "loader!qn", name = "b", descriptor = "(II)V")
	void close();

	@OriginalMember(owner = "loader!qn", name = "a", descriptor = "(I[I)V")
	void write(@OriginalArg(1) int[] arg0);

	@OriginalMember(owner = "loader!qn", name = "a", descriptor = "(II)V")
	void discardBuffer();
}
