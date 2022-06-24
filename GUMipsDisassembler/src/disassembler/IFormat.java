package disassembler;

/**
 * A class for Iformat MIPS instruction disassembly. 
 */

public class IFormat {
	private String reg1;
	private String reg2;
	private short offset;
	private String assembly;

	public IFormat() {
	}

	/**
	 * This method takes a integer opcode and returns it's corresponding string
	 * value from the following 5 options: "lw", "sw", "beq", "bne", "invalid".
	 * 
	 * @param int opcode
	 * @return String result - a representation of the opcode
	 */
	public String convert(int opcode) {
		String result = null;

		if (opcode == 0x23) {
			result = "lw";
		} else if (opcode == 0x2b) {
			result = "sw";
		} else if (opcode == 0x4) {
			result = "beq";
		} else if (opcode == 0x5) {
			result = "bne";
		} else if (opcode == 0X28) {
			result = "sb";
		} else if (opcode == 0X20) {
			result = "lb";
//		} else if (opcode == 0X0) {
//			result = "noOp";

		} else {
			System.out.println("Opcode is not recognized");
		}
		return result;
	}

	/**
	 * This method takes a MIPS instruction and returns it's corresponding decimal
	 * register value
	 * 
	 * @param instruction
	 * @return String representation of register 2 (sd)
	 */
	public String reg2(int instruction) {
		int bitShift = 16;
		int mask = 0b111110000000000000000;
		int reg2 = (instruction & mask) >>> bitShift;
		return ("$" + Integer.toString(reg2));
	}

	/**
	 * This method takes a MIPS instruction and returns it's corresponding decimal
	 * register value
	 * 
	 * @param instruction
	 * @return String representation of register 1 (s1)
	 */
	public String reg1(int instruction) {
		int bitshift = 21;
		int mask = 0b00000011111000000000000000000000;
		int reg1 = (instruction & mask) >>> bitshift;
		return ("$" + Integer.toString(reg1));
	}

	/**
	 * This method calculates the offset of a MIPS instruction in I format It is not
	 * suitable for branch instructions.
	 * 
	 * @param instruction
	 * @return short offset
	 */
	public short offset(int instruction) {
		int mask = 0b1111111111111111;
		short offset = (short) (mask & instruction);
		return offset;
	}

	/**
	 * This method calculates the pc relative offset of a MIPS instruction and uses
	 * it to return the corresponding hex address It is only suitable for branch
	 * instructions.
	 * 
	 * @param instruction
	 * @param address
	 * @return a hex address
	 */
	public String relativeOffset(int instruction, int address) {
		int mask = 0b1111111111111111;
		short relativeOffset = (short) ((mask & instruction) << 2);
		int branchAddress = (address + relativeOffset);
		return Integer.toHexString(branchAddress);
	}

	/**
	 * This method takes a MIPS instruction and opcode and forms the assembly
	 * language instruction.
	 * 
	 * @param instruction
	 * @param opcode
	 * @return String assembly instruction
	 */
	public String dissassembleI(int instruction, String opcode) {
		reg2 = reg2(instruction);
		offset = offset(instruction);
		reg1 = reg1(instruction);
		assembly = opcode 
				+ " " 
				+ reg2 
				+ (",") 
				+ offset 
				+ "(" 
				+ reg1 
				+ ")";
		return assembly;
	}

	/**
	 * This method takes a MIPS instruction, opcode and starting address and forms
	 * the assembly language instruction. It is only for branch instructions.
	 * 
	 * @param instruction
	 * @param opcode
	 * @return String assembly instruction
	 */
	public String branchDissasemble(int instruction, String opcode, int address) {
		reg1 = reg1(instruction);
		reg2 = reg2(instruction);
		String branchAddress = relativeOffset(instruction, address);
		assembly = opcode 
				+ " " 
				+ reg2 
				+ (",") 
				+ reg1 
				+ " address " 
				+ branchAddress;
		return assembly;
	}

}
