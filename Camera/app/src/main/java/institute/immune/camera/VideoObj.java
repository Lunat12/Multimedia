package institute.immune.camera;

import android.net.Uri;

public class VideoObj {

    private String title, description, video;

    public VideoObj(String _title, String _description, String _video){
        title = _title;
        description = _description;
        video = _video;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Uri getVideo() {
        Uri uri = Uri.parse(video);
        return uri;
    }
}
