package data;

/**
 * Created by Administrator on 2016/6/11.
 */
public class UpMsg {
    private String text;//������Ϣ
    private String lng;//����
    private String lat;//γ��
    private String time;//ʱ��
    private  String location;//��λ
    private String photopathString;



    public String getPhotopathString() {
		return photopathString;
	}

	public void setPhotopathString(String photopathString) {
		this.photopathString = photopathString;
	}

	public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }




}
