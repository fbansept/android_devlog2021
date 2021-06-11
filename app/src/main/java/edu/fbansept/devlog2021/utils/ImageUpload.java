package edu.fbansept.devlog2021.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.util.Base64;

import org.apache.commons.io.IOUtils;

public class ImageUpload extends StringRequest {

    private Context context;
    private String imageBase64;

    public ImageUpload(Context context, Uri uri, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) throws IOException {
        super(Method.POST, url, listener, errorListener);
        this.context = context;

        String extension = getMimeType(uri);
        String prefix = "";
        if(extension.equals("image/jpeg")){
            prefix = "data:image/jpeg;base64,";
        } else if (extension.equals("image/png")){
            prefix = "data:image/png;base64,";
        } else {
            throw new IOException("L'image doit être en png ou en jpg");
        }

        InputStream is = context.getContentResolver().openInputStream(uri);
        byte[] encode = Base64.encode(IOUtils.toByteArray(is),
                Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

        imageBase64 = prefix + new String(encode);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return imageBase64.getBytes();
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "image/jpeg");
        return params;
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
}
