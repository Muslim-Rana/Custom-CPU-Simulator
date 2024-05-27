package arch.sm213.machine.student;

import machine.AbstractMainMemory;


/**
 * Main Memory of Simple CPU.
 *
 * Provides an abstraction of main memory (DRAM).
 */

public class MainMemory extends AbstractMainMemory {
  private byte [] mem;
  
  /**
   * Allocate memory.
   * @param byteCapacity size of memory in bytes.
   */
  public MainMemory (int byteCapacity) {
    mem = new byte [byteCapacity];
  }
  
  /**
   * Determine whether an address is aligned to specified length.
   * @param address memory address.
   * @param length byte length.
   * @return true iff address is aligned to length.
   */
  @Override public boolean isAccessAligned (int address, int length) {
    // TODO
    return address % length == 0;
  }
  
  /**
   * Convert an sequence of four bytes into a Big Endian integer.
   * @param byteAtAddrPlus0 value of byte with lowest memory address (base address).
   * @param byteAtAddrPlus1 value of byte at base address plus 1.
   * @param byteAtAddrPlus2 value of byte at base address plus 2.
   * @param byteAtAddrPlus3 value of byte at base address plus 3 (highest memory address).
   * @return Big Endian integer formed by these four bytes.
   */
  @Override public int bytesToInteger (byte byteAtAddrPlus0, byte byteAtAddrPlus1, byte byteAtAddrPlus2, byte byteAtAddrPlus3) {
    // TODO
    int result = 0;
    int shiftAmount = 24;

    int extendedByte1 = byteAtAddrPlus0 & 0xff;
    extendedByte1 = extendedByte1 << shiftAmount;
    shiftAmount = shiftAmount - 8;
    result = result | extendedByte1;

    int extendedByte2 = byteAtAddrPlus1 & 0xff;
    extendedByte2 = extendedByte2 << shiftAmount;
    shiftAmount = shiftAmount - 8;
    result = result | extendedByte2;

    int extendedByte3 = byteAtAddrPlus2 & 0xff;
    extendedByte3 = extendedByte3 << shiftAmount;
    shiftAmount = shiftAmount - 8;
    result = result | extendedByte3;

    int extendedByte4 = byteAtAddrPlus3 & 0xff;
    extendedByte4 = extendedByte4 << shiftAmount;
    shiftAmount = shiftAmount - 8;
    result = result | extendedByte4;

    return result;
  }
  
  /**
   * Convert a Big Endian integer into an array of 4 bytes organized by memory address.
   * @param  i an Big Endian integer.
   * @return an array of byte where [0] is value of low-address byte of the number etc.
   */
  @Override public byte[] integerToBytes (int i) {
    // TODO
    byte[] bytes = new byte[4];
    int shiftAmount = 24;

    int chunk1 = i >> shiftAmount;
    shiftAmount = shiftAmount - 8;
    chunk1 = chunk1 & 0xff;
    byte byte1 = (byte) chunk1;
    bytes[0] = byte1;

    int chunk2 = i >> shiftAmount;
    shiftAmount = shiftAmount - 8;
    chunk2 = chunk2 & 0xff;
    byte byte2 = (byte) chunk2;
    bytes[1] = byte2;

    int chunk3 = i >> shiftAmount;
    shiftAmount = shiftAmount - 8;
    chunk3 = chunk3 & 0xff;
    byte byte3 = (byte) chunk3;
    bytes[2] = byte3;

    int chunk4 = i >> shiftAmount;
    chunk4 = chunk4 & 0xff;
    byte byte4 = (byte) chunk4;
    bytes[3] = byte4;


    return bytes;
  }
  
  /**
   * Fetch a sequence of bytes from memory.
   * @param address address of the first byte to fetch.
   * @param length  number of bytes to fetch.
   * @throws InvalidAddressException  if any address in the range address to address+length-1 is invalid.
   * @return an array of byte where [0] is memory value at address, [1] is memory value at address+1 etc.
   */
  @Override public byte[] get (int address, int length) throws InvalidAddressException {
    // TODO

    if (address + length - 1 >= mem.length || address < 0) {
        throw new InvalidAddressException();
    }

    byte[] bytes = new byte[length];
    int index = 0;

    for (int i = address; i < address + length; i++) {
        bytes[index] = mem[i];
        index = index + 1;
    }

    return bytes;
  }
  
  /**
   * Store a sequence of bytes into memory.
   * @param  address                  address of the first byte in memory to recieve the specified value.
   * @param  value                    an array of byte values to store in memory at the specified address.
   * @throws InvalidAddressException  if any address in the range address to address+value.length-1 is invalid.
   */
  @Override public void set (int address, byte[] value) throws InvalidAddressException {
    // TODO

    if (address + value.length - 1 >= mem.length || address < 0) {
        throw new InvalidAddressException();
    }

    int location = address;
    for (byte b : value) {
        mem[location] = b;
        location = location + 1;
    }

  }
  
  /**
   * Determine the size of memory.
   * @return the number of bytes allocated to this memory.
   */
  @Override public int length () {
    return mem.length;
  }
}
