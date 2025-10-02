package model;

public class MusicalEvent extends Event {
    private String singer;
    public MusicalEvent(int id, String title, String location, String theaterName, String date, String singer) {
        super(id, title, location, theaterName, date);
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String s) {
        singer = s;
    }

    public String toString(){
        return super.toString() + ", " + singer;
    }

    @Override
    public String toCSV(){
        return super.toCSV() +","+ singer;
    }
}
