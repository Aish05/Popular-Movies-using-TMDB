package moviesapp.com.myapplication.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMain implements Parcelable {

    @SerializedName("page")

    private Integer page;
    @SerializedName("total_results")

    private Integer totalResults;
    @SerializedName("total_pages")

    private Integer totalPages;
    @SerializedName("results")

    private List<PopularResult> results = null;

    public PopularMain(Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        if (in.readByte() == 0) {
            totalResults = null;
        } else {
            totalResults = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPages = null;
        } else {
            totalPages = in.readInt();
        }
        results = in.createTypedArrayList(PopularResult.CREATOR);
    }

    public static final Creator<PopularMain> CREATOR = new Creator<PopularMain>() {
        @Override
        public PopularMain createFromParcel(Parcel in) {
            return new PopularMain(in);
        }

        @Override
        public PopularMain[] newArray(int size) {
            return new PopularMain[size];
        }
    };

    public PopularMain() {

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<PopularResult> getResults() {
        return results;
    }

    public void setResults(List<PopularResult> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (page == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(page);
        }
        if (totalResults == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalResults);
        }
        if (totalPages == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalPages);
        }
        parcel.writeTypedList(results);
    }
}
