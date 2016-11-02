package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SetPower extends Message{

    private boolean level;

    public SetPower() {
        setTagged(true);
        setType(21);
        setSource("192.168.1.255");
        setTarget("0:0:0:0:0:0");
    }

    public SetPower(byte[] bytes) {
        super(bytes);
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public boolean getLevel() {
        return this.level;
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        ByteBuffer buf = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);

        if (level) {
            buf.putShort((short)65535);
        } else {
            buf.putShort((short)0);
        }
        return buf;
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        int i = (buf.getShort() & 0xffff);
        if (i == 65535) {
            level = true;
        } else if (i == 0) {
            level = false;
        } else {
            throw new IllegalArgumentException("Power level must be 0 or 65535");
        }
    }

    @Override
    public String toString() {
        return "SetPower" + super.toString() + "--payload(level=" + level + ")";
    }
}
