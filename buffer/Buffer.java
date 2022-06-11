package buffer;
public class Buffer {
    public int offset;
    public int length;
    public String message;
    
    public Buffer(byte[] pkg){
        this.setBuffet(pkg);
    }
    public Buffer(int id, int len, String pkg){
        this.offset= id;
        this.length = len;
        byte [] m = pkg.getBytes();
        int len_pkg = pkg.length();
        byte[] msgBytes = new byte[12];
        for (int i = 0; i < len_pkg; i++) {
            msgBytes[i] = m[i];
        }
        this.message = new String(msgBytes);
    }

    public void setBuffet(byte[] pkg){
        byte [] m = pkg;

        int offset = 0;
        byte[] offsetB = {m[0], m[1]};
        for (byte b :offsetB ) {
            offset = (offset << 8) + (b & 0xFF);
        }
        this.offset = offset;

        byte[] lengBytes = {m[2], m[3]};
        int leng = 0;
        for (byte b :lengBytes ) {
            leng = (leng << 8) + (b & 0xFF);
        }
        this.length = leng;

        byte[] msgBytes = new byte[12];
        for (int i = 0; i < 12; i++) {
            msgBytes[i] = m[i+4];
        }
        this.message = new String(msgBytes);
    }

    public  byte[] Tobytes(){
        byte[] r = new byte[16];

        byte[] offsetB = this.intToByteArray(this.offset);
        byte[] lenB = this.intToByteArray(this.length);

        r[0] = offsetB[0];
        r[1] = offsetB[1];
        r[2] = lenB[0];
        r[3] = lenB[1];

        byte[] msgB = this.message.getBytes();
        for (int i = 0; i < this.message.length(); i++) {
            r[i+4] = msgB[i];
        }
        
        return r;
    }

    public final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 8),
                (byte)value };
    }
}
