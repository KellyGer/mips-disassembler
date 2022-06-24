package disassembler;

/**
 * This is a partial disassembler for MIPS instructions. It has an array of
 * 32-bit machine instructions embedded into the program and outputs the
 * original source instructions that created them.
 * 
 * @author Kelly Geraghty (Kellyann Gannon)
 */
public class Main {

	public static void main(String[] args) {

		int[] inputInstructions = {
				0x8E75FFF8, 0x032BA020, 0x8CE90014, 
				0x12A90003, 0x022DA822, 0xADB30020, 
				0x02697824, 0xAE8FFFF4, 0x018C6020, 
				0x02A4A825, 0X158FFFF7, 0x8ECDFFF0 };

		int address = 0x9A040;

		RFormat rformat = new RFormat();
		IFormat iformat = new IFormat();

		for (int i : inputInstructions) {
			System.out.print(Integer.toHexString(address) + " ");
			address += 0x4;

			int opcode = rformat.getOpcode(i);

			if (opcode == 0) {
				System.out.println(rformat.dissassemble(i));
			} else {
				String op = iformat.convert(opcode);

				if (op.contentEquals("lw") || op.contentEquals("sw") || op.contentEquals("noOp")
						|| op.contentEquals("lb") || op.contentEquals("sb")) {
					System.out.println(iformat.dissassembleI(i, op));
				} else {
					System.out.println(iformat.branchDissasemble(i, op, address));
				}
			}

		}
	}

}
