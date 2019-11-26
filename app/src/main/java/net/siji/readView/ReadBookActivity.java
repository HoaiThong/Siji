package net.siji.readView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import net.siji.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class ReadBookActivity extends AppCompatActivity {


    // Nội dung của text
    String story = "Giang Trần cảm giác dường như trong đầu mình loạn thành một đoàn bột nhão, cảm giác này giống như là đang nằm mơ, nhưng nằm mơ thì không thể nào chân thật đến mức này được. Mỗi một tấc da thịt quanh thân, mỗi một khối xương cốt, đều đau đớn không chịu nổi.<br /><br />- Ta đã chết rồi sao? Đang chịu nỗi khổ luyện thân trong mấy tầng địa ngục sao ?<br /><br />Trực giác đầu tiên của Giang Trần là cảm giác mình đã chết. Thế nhưng, Sinh Mệnh Khí Tức như có như không của thân thể, lại phảng phất đang nhắc nhở hắn, hắn còn sống.<br /><br />Cũng không biết cảm giác như vậy giằng co bao lâu. Trong giây lát, Giang Trần cố gắng mở mắt ra, lại phát hiện mình nằm ở trong một bộ quan tài.<br /><br />Nằm ở trong quan tài? Nói như vậy, mình là thật đã chết? Giang Trần lòng tràn đầy bi thương.<br /><br />- Đáng tiếc đáng buồn, Giang Trần ta thân là Thiên Đế chi tử, lại trời sinh Thái Âm Chi Thể, không thể tu luyện võ đạo. Dù có phụ hoàng luyện chế Nhật Nguyệt Thần Đan cho ta, để cho ta thọ cùng trời đất, kết quả Thiên Đạo hạo kiếp buông xuống, lại thành vướng víu của phụ thân, cuối cùng tránh không được vận mệnh bỏ mình. . .<br /><br />- Ồ? Kinh mạch của ta là chuyện gì xảy ra? Thậm chí có chân khí chạy! Tuy rất nhỏ. . . Không đúng! Cái này. . . Đây không phải thân thể của ta, đây tuyệt đối không phải là nhục thể của ta! Ta trời sinh Thái Âm Chi Thể, trong cơ thể làm sao có thể có chân khí du động?<br /><br />- Hơn nữa, nếu như ta đã chết, trong cơ thể sao có thể có chân khí du động?<br /><br />Giang Trần nằm ở trong quan tài, đầu óc phảng phất có một dòng điện kích động mà qua. Đồng thời, hắn cũng phát hiện, bộ dạng thân thể nằm ở trong quan tài này, căn bản không phải bản thân của hắn.<br /><br />- Cái này. . . Đây rốt cuộc xảy ra chuyện gì? Thân thể này của ai?<br /><br />Cái phát hiện ngạc nhiên này, làm cho Giang Trần vừa mừng vừa sợ.<br /><br />Rất nhanh, hắn ở trong thân thể này, đọc đến một ít trí nhớ lưu lại.<br /><br />- Chủ nhân thân thể này cũng gọi là Giang Trần? Chư hầu chi tử của Đông Phương Vương Quốc Giang Hãn Châu. Tên là trùng tên, nhưng điều này hiển nhiên không phải ta a! Ta đường đường Thiên Đế chi tử, như thế nào thành chư hầu chi tử của Vương Quốc phàm tục?<br /><br />Trong đầu Giang Trần tràn ngập nghi vấn.<br /><br />- Chẳng lẽ ta thật sự đã ở trong Chư Thiên hạo kiếp chết đi? Cái này chẳng lẽ là chuyển sinh ký túc trong truyền thuyết? Chư Thiên nghiền nát, Luân Hồi sụp đổ, theo lý thuyết ta ở trong hạo kiếp đã chết, không có khả năng vào luân hồi. Nói như vậy, ta thật sự chuyển sinh?<br /><br />Trải qua mảnh vỡ ý thức của tiền nhiệm Giang Trần, Giang Trần rốt cục xác nhận sự thật này.<br /><br />- Không thể tưởng được, kiếp trước ta là Thiên Đế chi tử, nhưng lại là Thái Âm Chi Thể không thể tu luyện. Sau khi chuyển sinh, thành chư hầu chi tử của Vương Quốc phàm tục, ngược lại đã có tư chất tu luyện. Cái này thật sự là thiên đại châm chọc a!<br /><br />- Chư Thiên nghiền nát, trật tự băng diệt. . . Giang Trần ta kiếp trước sống trăm vạn năm, mặc dù cùng Nhật Nguyệt đồng thọ, nhưng hạo kiếp vừa đến, lại một chút bề bộn cũng không thể giúp. Thái Âm Chi Thể, không thể tu luyện, sinh tử cuối cùng không thể tự chủ. Vận mệnh tựa như bụi đất, gió thổi liền tán.<br /><br />Nghĩ đến kiếp trước nhiều loại tao ngộ, nghĩ đến kiếp trước phụ thân thân là Thiên Đế, không tiếc hao phí Thần Nguyên vì hắn luyện chế Nhật Nguyệt Thần Đan, để cho hắn dùng phàm thai chi thể, được Nhật Nguyệt chi thọ. Giang Trần không khỏi có chút thất thần.<br /><br />Hắn biết rõ, kiếp trước kiếp này sinh tử biệt ly, có lẽ là vĩnh biệt!<br /><br />Mặc dù kiếp trước hắn sống qua trăm vạn tuế nguyệt, mặc dù hắn có đại trí tuệ, nghĩ đến Thiên Đế phụ thân cẩn thận chiếu cố, liền lã chã rơi lệ.<br /><br />Hắn biết rõ, Chư Thiên nghiền nát, coi như là Thiên Đế, chỉ sợ cũng khó ở trong hạo kiếp may mắn thoát khỏi.<br /><br />Nhất niệm đến đây, Giang Trần không khỏi có chút bực mình.<br /><br />Bất quá, trong nháy mắt linh hồn của hắn xúc động kinh mạch, giống như một đạo lôi điện kích xạ linh hồn của hắn. Như là người cực khát gặp được một đám cam tuyền!<br /><br />Trong kinh mạch, chân khí chậm rãi lưu động, tuy nhỏ yếu, nhỏ yếu đến cơ hồ muốn khô kiệt. Nhưng mà, đúng là một đạo chân khí yếu ớt này, lại như hỏa hoa nhen nhóm tánh mạng chi quang của hắn, đem những cảm xúc tiêu cực quét qua sạch sẽ.<br /><br />- Tu luyện! Ha ha, tu luyện! Chẳng bao lâu sau, đối với ta trời sinh Thái Âm Chi Thể mà nói, là một khái niệm xa xỉ hạng gì? Kiếp trước, ta không thể tu luyện, lại chưa từng hướng vận mệnh khuất phục. Hôm nay, ta chuyển sinh đạt được tư chất tu luyện, chẳng lẽ không phải vận mệnh chi môn mở ra cho ta một khe hở sao?<br /><br />- Ta là Thiên Đế chi tử, chưởng quản Thiên Lang Thư Uyển trăm vạn năm, Chư Thiên điển tịch, ta không chỗ nào không duyệt; Chư Thiên pháp môn, ta không gì không biết. Một thân đan đạo tu vi, càng là nổi tiếng Chư Thiên. Chỉ tiếc kiếp trước đầy bụng kinh luân, một bụng lý luận không thể thực tiễn. Hôm nay, được chuyển sinh Tạo Hóa, đã có tư chất tu luyện, Giang Trần ta càng có sợ gì? Có lý do gì cúi đầu nhận thua?<br /><br />Nghĩ đến đây, Giang Trần lập tức cảm thấy rộng mở trong sáng.<br /><br />Những cảm xúc tinh thần sa sút kia, cũng dần dần hóa giải không ít. Hắn cảm thấy, lần này chuyển sinh, có lẽ là vận mệnh chuyển cơ, là Đại Tạo Hóa của hắn!<br /><br />Đúng vậy, nguyên lai cái gọi là thân phận chư hầu chi tử của Giang Trần kia, từ góc độ địa vị kiếp trước của hắn đến xem, thật sự là nhỏ yếu đến không có ý nghĩa.<br /><br />Nhưng mà, thân phận lại thấp kém nhỏ yếu, có một điểm là hắn kiếp trước không có. Cái kia chính là tư cách tu luyện!<br /><br />Tư cách tu luyện, tựa như một cái lạch trời.<br /><br />Kiếp trước, hắn thân phận là cao quý, như Cửu Thiên Long Phượng, lại chỉ có thể ở phía sau nhìn người khác tu luyện.<br /><br />Kiếp nầy, hắn thân phận thấp kém, tựa như con sâu cái kiến, đã có tư cách từ hàng dưới chót bắt đầu cất bước.<br /><br />Tu luyện chi đạo vô cùng vô tận, chỉ cần có cơ duyên, Kim Lân ngộ phong, gặp gió có thể hóa rồng, con sâu cái kiến cũng đồng dạng có thể tiếu ngạo trời cao!<br /><br />Hôm nay, cơ duyên đến rồi!<br /><br />Thiên Đế chi tử, chưởng quản Thiên Lang Thư Uyển, trăm vạn năm tuế nguyệt, cơ hồ là đọc tận sách vở, kiến thức uyên bác. Nói hắn đầy bụng kinh luân, ý chí vạn tượng cũng không tính khoa trương.<br /><br />Đầy mình lý luận, từ Chư Thiên, cho tới phàm tục, có thể nói không có lĩnh vực mà Giang Trần hắn chưa quen thuộc.<br /><br />Ở kiếp trước, trong trăm vạn năm nhàm chán, Giang Trần không thể tu luyện, lại ưa thích dạy đồ đệ. Dùng đồ đệ làm thí nghiệm, đem lý luận biến thành thực tế.<br /><br />Trăm vạn năm thời gian, không biết sáng tạo ra bao nhiêu thiên tài.<br /><br />Cái gì là cơ duyên?<br /><br />Hắn chuyển sinh rồi, mang theo trí nhớ của Thiên Đế chi tử, nhận được thân thể chết oan của chư hầu chi tử Giang Trần.<br /><br />Kiếp trước ở trên người đồ đệ làm những thí nghiệm kia, hôm nay, rốt cục có thể tự mình thực hiện!<br /><br />Cái này là cơ duyên!<br /><br />Nhất niệm đến đây, Giang Trần nhịn không được vạn phần kích động.<br /><br />Vừa lúc đó, một thanh âm vỡ tan thanh thúy, truyền vào trong tai của hắn, phảng phất có đồ vật gì đó bị rớt bể.";
    String story2;// = this.getResources().getString(R.string.vbmt);
    // ImageView sẽ hiển thị trang
    ImageView image;

    // index: trang hiện tại; width, height: chiều dài, rộng màn hình
    int index = 0, width, height;

    ProgressDialog mProgressDialog;
    ProgressBar mProgressBar;
    String url = "https://docs.google.com/uc?id=12NwjLUh4LH3XP1DananzdYQZ9Luz-pMu";
    RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        //
        setContentView(R.layout.activity_read_book);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().hide();
        relativeLayout = findViewById(R.id.epub_reader);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getSupportActionBar().isShowing()) getSupportActionBar().hide();
                    else getSupportActionBar().show();
                    return true;
                } else
                    return false;
            }
        });
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
//        Display display = ((WindowManager) this
//                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//        width = display.getWidth();
//        height = display.getHeight();
        //

        Log.d("dir: ", getFilesDir().getPath());
        Log.d("dir: ", getFilesDir().getAbsolutePath());
        try {
            Log.d("dir: ", getFilesDir().getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("dir: ", getFilesDir().getParent());
        init();
    }

    public void init() {
//         instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Dowloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

//        mProgressBar = new ProgressBar(this,
//                null,
//                android.R.attr.progressBarStyleHorizontal);
//        mProgressBar.setIndeterminate(true);
        final DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.execute(url);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true); //cancel the task
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.epub_menu, menu);
        return true;
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String dir = getFilesDir() + "/" + context.getString(R.string.nomedia_document);
//                String dir = getFilesDir() + "/" + "nomedia";
                output = new FileOutputStream(dir + "/dowload.epub");
                Log.d("pathDowload", dir);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
//            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
//            mProgressBar.setIndeterminate(false);
//            mProgressBar.setMax(100);
//            mProgressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
//            mProgressBar.setVisibility(View.INVISIBLE);
            if (result != null) {
                Log.d("result Dowload", result.toString());
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

}