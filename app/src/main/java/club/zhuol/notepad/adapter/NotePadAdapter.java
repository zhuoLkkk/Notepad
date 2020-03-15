package club.zhuol.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import club.zhuol.notepad.NotepadActivity;
import club.zhuol.notepad.R;
import club.zhuol.notepad.bean.NotePadBean;

public class NotePadAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<NotePadBean> list;

    public NotePadAdapter(Context context, List<NotePadBean> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 通过inflate()方法加载Item界面的布局文件
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        /**
         * 如果为null 创建以个ViewHolder对象 通过getTag()方法将该对象添加到conventView中进行缓存
         * 否则 通过getTag()方法获取缓存的ViewHolder对象
         */
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.notepad_item_layout,null);
            viewHolder = new ViewHolder(convertView);
            //设置标签
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NotePadBean noteInfo = (NotePadBean) getItem(position);
        //获取内容和时间
        viewHolder.tvNotepadContent.setText(noteInfo.getNotpadContent());
        viewHolder.tvNotepadTime.setText(noteInfo.getNotpadTime());
        return convertView;
    }

    class ViewHolder {
        TextView tvNotepadContent;
        TextView tvNotepadTime;
        //绑定组件
        public ViewHolder(View view) {
            tvNotepadContent = view.findViewById(R.id.tv_content);
            tvNotepadTime = view.findViewById(R.id.tv_time);
        }
    }
}
