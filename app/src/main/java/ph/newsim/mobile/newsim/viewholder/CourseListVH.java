package ph.newsim.mobile.newsim.viewholder;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import org.apache.commons.lang3.text.WordUtils;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Course;

public class CourseListVH extends RecyclerView.ViewHolder{

//    public TextView mCourseCode;
//    public TextView mCourseDescription;
//    public TextView mCourseType;
//    public RoundedImageView mCourseImage;

    public TextView mCourseCode;
    public TextView mCourseDescription;
    public TextView mCourseDuration;
    public ImageView mThumbnail;

    public CourseListVH(View itemView) {
        super(itemView);
//        mCourseCode = (TextView) itemView.findViewById(R.id.label_course_code);
//        mCourseType = (TextView) itemView.findViewById(R.id.label_course_type);
//        mCourseDescription = (TextView) itemView.findViewById(R.id.label_course_description);
//        mCourseImage = (RoundedImageView) itemView.findViewById(R.id.img_course_image);

        mCourseCode = (TextView) itemView.findViewById(R.id.label_course_code);
        mCourseDescription = (TextView) itemView.findViewById(R.id.label_course_description);
        mCourseDuration = (TextView) itemView.findViewById(R.id.label_course_duration);
        mThumbnail = (ImageView) itemView.findViewById(R.id.img_course_thumbnail);

    }

    public void bindData(Context context, Course course){
        mCourseCode.setText(course.getCode());
        mCourseDescription.setText(course.getDescription());
        mCourseDuration.setText(course.getDuration());
        Glide.with(context).load(getThumbnail(course.getCode())).into(mThumbnail);

//        if (course.getType().equals(Course.TYPE_ANTI_PIRACY_AWARENESS_TRAINING)){
//            Glide.with(context).load(R.drawable.cover_course_anti_piracy).centerCrop().into(mCourseImage);
//        }else if (course.getType().equals(Course.TYPE_ASSESSMENT)){
//            Glide.with(context).load(R.drawable.cover_course_assessment).centerCrop().into(mCourseImage);
//        }else if (course.getType().equals(Course.TYPE_COMMON)){
//            Glide.with(context).load(R.drawable.cover_course_common).centerCrop().into(mCourseImage);
//        }else if (course.getType().equals(Course.TYPE_DECK)){
//            Glide.with(context).load(R.drawable.cover_course_deck).centerCrop().into(mCourseImage);
//        }else if (course.getType().equals(Course.TYPE_ENGINE)){
//            Glide.with(context).load(R.drawable.cover_course_engine).centerCrop().into(mCourseImage);
//        }else if (course.getType().equals(Course.TYPE_TRAINING)){
//            Glide.with(context).load(R.drawable.cover_course_training).centerCrop().into(mCourseImage);
//        }else{
//            Glide.with(context).load(R.drawable.cover_course_others).centerCrop().into(mCourseImage);
//        }
//        mCourseType.setText(WordUtils.capitalizeFully(course.getType()));
    }

    public int getThumbnail(String title){
        String uppercaseTitle = title.toUpperCase();
        char firstLetter;
        firstLetter = uppercaseTitle.charAt(0);
        switch (firstLetter){
            case 'A':
                return R.drawable.thumb_a;
            case 'B':
                return R.drawable.thumb_b;
            case 'C':
                return R.drawable.thumb_c;
            case 'D':
                return R.drawable.thumb_d;
            case 'E':
                return R.drawable.thumb_e;
            case 'F':
                return R.drawable.thumb_f;
            case 'G':
                return R.drawable.thumb_g;
            case 'H':
                return R.drawable.thumb_h;
            case 'I':
                return R.drawable.thumb_i;
            case 'J':
                return R.drawable.thumb_j;
            case 'K':
                return R.drawable.thumb_k;
            case 'L':
                return R.drawable.thumb_l;
            case 'M':
                return R.drawable.thumb_m;
            case 'N':
                return R.drawable.thumb_n;
            case 'O':
                return R.drawable.thumb_o;
            case 'P':
                return R.drawable.thumb_p;
            case 'Q':
                return R.drawable.thumb_q;
            case 'R':
                return R.drawable.thumb_r;
            case 'S':
                return R.drawable.thumb_s;
            case 'T':
                return R.drawable.thumb_t;
            case 'U':
                return R.drawable.thumb_u;
            case 'V':
                return R.drawable.thumb_v;
            case 'W':
                return R.drawable.thumb_w;
            case 'X':
                return R.drawable.thumb_x;
            case 'Y':
                return R.drawable.thumb_y;
            case 'Z':
                return R.drawable.thumb_z;
            default:
                return R.drawable.thumb_a;
        }
    }

}
