package arch.sm213.machine.student;

import jdk.jfr.internal.tool.Main;
import machine.AbstractMainMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainMemoryTest {
    private MainMemory mainMemory;

    @BeforeEach
    public void runBefore() {
        mainMemory = new MainMemory(13);
    }

    //test below for the constructor of the MainMemory class
    @Test
    public void testConstructor() {
        assertEquals(13, mainMemory.length());
    }


    //tests below for the isAccessAligned() method, true and false cases:
    @Test
    public void testIsAccessAlignedTrue() {
        assertTrue(mainMemory.isAccessAligned(0, 4));
        assertTrue(mainMemory.isAccessAligned(4, 4));
        assertTrue(mainMemory.isAccessAligned(8, 4));
        assertTrue(mainMemory.isAccessAligned(12, 4));
        assertTrue(mainMemory.isAccessAligned(0, 2));
        assertTrue(mainMemory.isAccessAligned(4, 2));
    }

    @Test
    public void testIsAccessAlignedFalse() {
        assertFalse(mainMemory.isAccessAligned(1, 4));
        assertFalse(mainMemory.isAccessAligned(2, 4));
        assertFalse(mainMemory.isAccessAligned(3, 4));
        assertFalse(mainMemory.isAccessAligned(5, 4));
        assertFalse(mainMemory.isAccessAligned(9, 4));
        assertFalse(mainMemory.isAccessAligned(11, 4));
        assertFalse(mainMemory.isAccessAligned(1, 2));
        assertFalse(mainMemory.isAccessAligned(10, 3));
    }

    //tests below for the bytesToInteger() method

    @Test
    public void testBytesToIntegerNegativeBytes() {
        byte b0 = -1;
        assertEquals(-1, mainMemory.bytesToInteger(b0,b0,b0,b0));

        byte b1 = -1;
        byte b2 = 1;
        assertEquals(-16711423, mainMemory.bytesToInteger(b1,b2,b2,b2));

        b1 = 1;
        b2 = -1;
        assertEquals(33554431, mainMemory.bytesToInteger(b1,b2,b2,b2));
        assertEquals(33489407, mainMemory.bytesToInteger(b1,b2,b1,b2));

    }

    @Test
    public void testBytesToIntegerZero() {
        byte b = 0;
        assertEquals(0, mainMemory.bytesToInteger(b,b,b,b));
    }

    @Test
    public void testBytesToIntegerAllBitsSet() {
        byte b = (byte) 0xff;
        assertEquals(-1, mainMemory.bytesToInteger(b,b,b,b));
    }

    @Test
    public void testBytesToIntegerMaxPositive() {
        byte b1 = (byte) 0x7f;
        byte b2 = (byte) 0xff;
        assertEquals(Integer.MAX_VALUE, mainMemory.bytesToInteger(b1,b2,b2,b2));
    }

    @Test
    public void testBytesToIntegerMaxNegative() {
        byte b1 = (byte) 0x80;
        byte b2 = (byte) 0x00;
        assertEquals(Integer.MIN_VALUE, mainMemory.bytesToInteger(b1,b2,b2,b2));
    }

    @Test
    public void testBytesToIntegerRandomValue() {
        byte b1 = (byte) 0x01;
        byte b2 = (byte) 0x02;
        byte b3 = (byte) 0x03;
        byte b4 = (byte) 0x04;
        assertEquals(16909060, mainMemory.bytesToInteger(b1,b2,b3,b4));
    }

    //tests below for integerToBytes() method

    @Test
    public void testIntegerToBytesZero() {
        byte[] expected = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        byte[] result = mainMemory.integerToBytes(0);
        assertEquals(expected.length, result.length);

        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void testIntegerToBytesMaxNegative() {
        byte[] expected = new byte[]{(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        byte[] result = mainMemory.integerToBytes(Integer.MIN_VALUE);
        assertEquals(expected.length, result.length);

        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void testIntegerToBytesMaxPositive() {
        byte[] expected = new byte[]{(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        byte[] result = mainMemory.integerToBytes(Integer.MAX_VALUE);
        assertEquals(expected.length, result.length);

        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void testIntegerToBytesRandomPositive() {
        byte[] expected = new byte[]{(byte) 0x40, (byte) 0x12, (byte) 0x34, (byte) 0x5A};
        byte[] result = mainMemory.integerToBytes(0x4012345A);
        assertEquals(expected.length, result.length);

        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @Test
    public void testIntegerToBytesRandomNegative() {
        byte[] expected = new byte[]{(byte) 0xff, (byte) 0x5D, (byte) 0xAA, (byte) 0x4C};
        byte[] result = mainMemory.integerToBytes(0xFF5DAA4C);
        assertEquals(expected.length, result.length);

        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    //tests below for get() method

    @Test
    public void testGetExceptionThrown() {
        //recall that mainMemory was initialized with a size of 13

        try {
            mainMemory.get(12, 2);
            fail("should have thrown an exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.get(1, 13);
            fail("should have thrown an exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.get(8, 6);
            fail("should have thrown an exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.get(-1, 10);
            fail("should have thrown an exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }
    }

    @Test
    public void testGetNoException() {
        //recall that mainMemory was initialized with a size of 13
        byte[] expected = new byte[]{(byte) 0x00};

        try {
            byte[] result = mainMemory.get(12, 1);
            assertEquals(expected.length, result.length);

            for (int i = 0; i < result.length; i++) {
                assertEquals(expected[i], result[i]);
            }

        } catch (AbstractMainMemory.InvalidAddressException e) {
            fail("should NOT have thrown an exception");
        }
    }

    @Test
    public void testGetNoException2() {
        //recall that mainMemory was initialized with a size of 13
        byte[] expected = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};

        try {
            //assuming a correct implementation of set()
            mainMemory.set(0, new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04});
        } catch (AbstractMainMemory.InvalidAddressException e) {
            fail("the set() method failed");
        }


        try {
            byte[] result = mainMemory.get(0, 4);
            assertEquals(expected.length, result.length);

            for (int i = 0; i < result.length; i++) {
                assertEquals(expected[i], result[i]);
            }

        } catch (AbstractMainMemory.InvalidAddressException e) {
            fail("should NOT have thrown an exception");
        }
    }

    //below are tests for the set method

    @Test
    public void testSetExceptionThrownPositiveAddress() {
        //recall the memory was initialized with a length of 13
        byte[] value = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x0A};

        try {
            mainMemory.set(10, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.set(11, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.set(12, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.set(13, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }
    }

    @Test
    public void testSetExceptionThrownNegativeAddress() {
        //recall the memory was initialized with a length of 13
        byte[] value = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x0A};

        try {
            mainMemory.set(-1, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.set(-2, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }

        try {
            mainMemory.set(-3, value);
            fail("should have thrown an invalid address exception");
        } catch (AbstractMainMemory.InvalidAddressException e) {
            //expected case
        }
    }

    @Test
    public void testSetNoException() {
        //recall the memory was initialized with a length of 13
        byte[] value = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x0A};

        try {
            mainMemory.set(0, value);

            try {
                //assuming a correct implementation of get() method
                byte[] result = mainMemory.get(0, 4);
                assertEquals(value.length, result.length);

                for (int i = 0; i < value.length; i++) {
                    assertEquals(value[i], result[i]);
                }

            } catch (AbstractMainMemory.InvalidAddressException e) {
                fail("the get method threw an exception");
            }

        } catch (AbstractMainMemory.InvalidAddressException e) {
            fail("should NOT have thrown an invalid address exception");
        }
    }

    @Test
    public void testSetNoException2() {
        //recall the memory was initialized with a length of 13
        byte[] value = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x0A};

        try {
            mainMemory.set(9, value);

            try {
                byte[] result = mainMemory.get(9, 4);
                assertEquals(value.length, result.length);

                for (int i = 0; i < value.length; i++) {
                    assertEquals(value[i], result[i]);
                }

            } catch (AbstractMainMemory.InvalidAddressException e) {
                fail("the get method threw an exception");
            }

        } catch (AbstractMainMemory.InvalidAddressException e) {
            fail("should NOT have thrown an invalid address exception");
        }
    }

    //below are the tests for the length() method

    @Test
    public void testLength() {
        MainMemory testMem = new MainMemory(20);
        assertFalse(19 == testMem.length());
        assertEquals(20 ,testMem.length());
        assertFalse(21 == testMem.length());
    }


}
