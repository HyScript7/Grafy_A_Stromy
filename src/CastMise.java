public record CastMise(String nazev, int casTrvaniVMinutach) {
    @Override
    public String toString() {
        return nazev + ", " + casTrvaniVMinutach + " min";
    }
}
