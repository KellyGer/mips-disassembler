package disassembler;

/**
 * A class for Rformat MIPS instruction disassembly.
 */

public class RFormat {

	private String sourceReg1;
	private String sourceReg2;
	private String destReg;
	private String function;
	private String assembly;

	/**
	 * This method takes rformat MIPS instruction and returns a string
	 * representation of it's corresponding function from one of the following
	 * options, "add", "sub", "and", "or", "slt" "Invalid"
	 * 
	 * @param instruction
	 * @return String representation of the function
	 */

	public String getFunction(int instruction) {
		int functionMask = 0b111111;
		int maskedInstruction = (functionMask & instruction);

		if (maskedInstruction == 0x20) {
			function = "add";
		} else if (maskedInstruction == 0x22) {
			function = "sub";
		} else if (maskedInstruction == 0x24) {
			function = "and";
		} else if (maskedInstruction == 0x25) {
			function = "or";
		} else if (maskedInstruction == 0x2a) {
			function = "slt";
		} else {
			function = "nop";
		}
		return function;
	}

	/**
	 * This method takes a MIPS instruction and forms the assembly language
	 * instruction. It is only for R format instructions.
	 * 
	 * @param instruction
	 * @return
	 */
	public String dissassemble(int instruction) {
		function = getFunction(instruction);
		destReg = getDestinationReg(instruction);
		sourceReg1 = getsourceReg1(instruction);
		sourceReg2 = getSourceReg2(instruction);
		assembly = function + " " + destReg + "," + sourceReg1 + ',' + sourceReg2;

		return assembly;

	}

	/**
	 * This method takes a MIPS instruction and returns it's corresponding decimal
	 * register value
	 * 
	 * @param instruction
	 * @return String representation of destination register (ds)
	 */
	public String getDestinationReg(int instruction) {
		int bitShift = 11;
		int destMask = 0b1111100000000000;
		int destinationReg = (instruction & destMask) >>> bitShift;
		return ("$" + Integer.toString(destinationReg));

	}

	/**
	 * This method takes a MIPS instruction and returns it's corresponding decimal
	 * register value
	 * 
	 * @param instruction
	 * @return String representation of source register (s1)
	 */

	public String getsourceReg1(int instruction) {
		int bitShift = 21;
		int sourceReg1Mask = 0b11111000000000000000000000;
		int reg1 = (instruction & sourceReg1Mask) >>> bitShift;
		return ("$" + Integer.toString(reg1));

	}

	/**
	 * This method takes a MIPS instruction and returns it's corresponding decimal
	 * register value
	 * 
	 * @param instruction
	 * @return String representation of destination register (s2)
	 */
	public String getSourceReg2(int instruction) {
		int bitShift = 16;
		int sourceReg2Mask = 0b111110000000000000000;
		int reg2 = (instruction & sourceReg2Mask) >>> bitShift;
		return ("$" + Integer.toString(reg2));
	}

	/**
	 * This method takes a MIPS instruction (rformat or iformat) and returns it's
	 * opcode
	 * 
	 * @param instruction
	 * @return String representation of destination register (ds)
	 */
	public int getOpcode(int instruction) {

		int bitShift = 26;
		int mask = 0b11111100000000000000000000000000;
		int opcode = (instruction & mask) >>> bitShift;

		return opcode;
	}
}
