# 期中：NoteBook

## 1.添加时间戳

![](https://ws3.sinaimg.cn/large/005BYqpggy1g36shp7n9wj30al0hot99.jpg)

在item_note.xml文件中添加一个相对布局，在相对布局中添加大概内容和时间戳两个TextView，

``

```
<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/id_tv_note_summary"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:padding="10dp"
        android:textColor="#727272"
        android:textSize="15sp" />

    <TextView
        android:id="@+id/id_tv_note_create_time"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:padding="10dp"
        android:textColor="#727272"
        android:textSize="15sp" />
</RelativeLayout>
```

获取时间

``

```
private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

public static String formatDate(Date date) {
    return dateFormat.format(date);
}
```

在创建笔记的点击事件中，将时间存到数据库中

``

```
ContentValues values = new ContentValues();
String modifydate=TextFormatUtil.formatDate(new Date());
values.put("title", title);
values.put("content", content);
values.put("create_time", mNote.getCreateTime());
values.put("modifyTime",modifydate);//插入修改时间
```

将时间戳显示在界面

``

```
ViewHolder holder = (ViewHolder) view.getTag();
String title = cursor.getString(cursor.getColumnIndex("title"));
holder.mTvTitle.setText(title);
holder.mTvContent.setText(TextFormatUtil.getNoteSummary(cursor.getString(cursor.getColumnIndex("content"))));
holder.mTvCreateTime.setText("修改时间:" +cursor.getString(cursor.getColumnIndex("modifyTime")));
```

## 2.笔记本查询功能

![](https://ws3.sinaimg.cn/large/005BYqpggy1g36stmako8j30ao0howeo.jpg)

查询界面

``

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/id_et_search_title"
        android:layout_width="200dp"
        android:layout_height="45dp"
        android:inputType="text" />


    <Button
        android:id="@+id/id_btn_search"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:text="@string/searchNote" />

    <ListView
        android:layout_below="@id/id_et_search_title"
        android:id="@+id/id_lv_found_note"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</RelativeLayout>
```

查询函数，

``

```
mBtnQuery.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String title = mEtSearch.getText().toString();
        if (title.length() > 0 && title != null) {
            mCursor = mNoteDAO.queryNote("title like ? ", new String[]{"%" + title + "%"},null);
        }
        if (!mCursor.moveToNext()) {
            Toast.makeText(getActivity(), "没有这个结果", Toast.LENGTH_SHORT).show();
        }
        mAdapter = new ShowNoteAdapter(getActivity(), mCursor);
        mLvResult.setAdapter(mAdapter);
    }
});
mLvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = (Cursor) mAdapter.getItem(position); // CursorAdapter中getItem()返回特定的cursor对象
        int itemID = c.getInt(c.getColumnIndex("_id"));
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.SENDED_NOTE_ID, itemID);
        startActivity(intent);
    }
});
```

## 3.扩展功能-UI美化

![](https://ws3.sinaimg.cn/large/005BYqpggy1g36t1f1sw7j30as0ht74u.jpg) ![](https://ws3.sinaimg.cn/large/005BYqpggy1g36t2sk9k9j30as0hxaah.jpg)

UI美化包括界面风格，添加笔记本按钮，抽屉菜单图标，抽屉菜单

笔记本模块的UI修改

``

```
<ListView
    android:id="@+id/id_lv_navi"
    android:layout_width="240dp"
    android:layout_height="match_parent"
    android:layout_gravity="start"
    android:background="@color/navi_divider"
    android:divider="@color/navi_divider"
    android:dividerHeight="0dp" />
```

侧边菜单栏的创建

``

```
mFragments[0] = new AllNotesFragment();
mFragments[1] = new SearchNoteFragment();
mFragments[2] = new SettingFragment();
mFragments[3]=new Sort_by_title();
```

toolBar的添加

``

```
<android.support.v7.widget.Toolbar
    xmlns:android="http://schemas.android.com/apk/res/android"

    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary" />
```

## 4.扩展功能-大致内容的查阅

在NoteList中添加部分内容，使得笔记更容易被识别，方便使用者使用

![](https://ws3.sinaimg.cn/large/005BYqpggy1g36tefmjhnj309n0ext9a.jpg) ![](https://ws3.sinaimg.cn/large/005BYqpggy1g36tetou17j309s0cu75m.jpg)

添加工具类，处理笔记本内容

``

```
public static String getNoteSummary(String content) {
    if (content.length() > 10) {
        StringBuilder sb = new StringBuilder(content.substring(0, 10));
        sb.append("...");
        return sb.toString();
    }
    return content;
}
```

`holder.mTvContent.setText(TextFormatUtil.getNoteSummary(cursor.getString(cursor.getColumnIndex("content"))));//插入大致内容，方便使用者使用`

## 5.扩展功能-排序

可以根据文本标题排序

![](https://ws3.sinaimg.cn/large/005BYqpggy1g36thrrqovj30a10g7q3j.jpg) ![](https://ws3.sinaimg.cn/large/005BYqpggy1g36tiilqmij309w0fcjrr.jpg)

![](https://ws3.sinaimg.cn/large/005BYqpggy1g36tjzv4oej30a20f874v.jpg)

通过修改DAO层的query，添加排序功能

``

```
public Cursor queryNote(String selection, String[] selectionArgs,String order) {
    SQLiteDatabase db = mHelper.getReadableDatabase();
    Cursor c = db.query(false, DBHelper.DB_NAME, null, selection, selectionArgs
            , null, null, order, null);
    return c;
}
```

`mCursor = mNoteDAO.queryNote(null, null,"title");`

``

```
mAdapter = new ShowNoteAdapter(getActivity(), mCursor);
getLoaderManager().initLoader(0, null, this);
mLvNotes.setAdapter(mAdapter);
mLvNotes.setOnItemClickListener(this);
registerForContextMenu(mLvNotes);
```

