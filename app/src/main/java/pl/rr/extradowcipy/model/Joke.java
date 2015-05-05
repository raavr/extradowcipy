package pl.rr.extradowcipy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafal on 2015-04-20.
 */
public class Joke implements Parcelable{

    private int id;
    private String content;
    private Category category;
    private boolean isFav;

    public Joke(int id, String content, Category category, boolean isFav) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.isFav = isFav;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean isFav) {
        this.isFav = isFav;
    }

    protected Joke(Parcel in) {
        id = in.readInt();
        content = in.readString();
        category = (Category) in.readValue(Category.class.getClassLoader());
        isFav = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeValue(category);
        dest.writeByte((byte) (isFav ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Joke> CREATOR = new Parcelable.Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel in) {
            return new Joke(in);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };
}
