package doctrina.Utils;

public abstract class BitMask<E extends Enum<E>> {

    private int bitMask;

    public BitMask() {
        bitMask = 0;
    }

    public BitMask(BitMask<E> other) {
        this.bitMask = other.bitMask;
    }

    public void clear() {
        bitMask = 0;
    }

    public void disable(E e) {
        bitMask &= ~(1 << e.ordinal());
    }

    public void enable(E e) {
        bitMask |= 1 << e.ordinal();
    }

    public void enableAll() {
        bitMask = ~0;
    }

    public boolean isOn(E e) {
        return (bitMask & (1 << e.ordinal())) != 0;
    }


}
