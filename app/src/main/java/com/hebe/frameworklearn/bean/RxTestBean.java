package com.hebe.frameworklearn.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hebe Han
 * @date 2018/7/6 10:26
 */
public class RxTestBean implements Parcelable{
    String name;
    int age;
    List<String> list = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RxTestBean(String name, int age) {
        this.name = name;
        this.age = age;
        list.add("test");
        list.add("test1");
        list.add("test2");
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public RxTestBean() {
        list.add("test");
        list.add("test1");
        list.add("test2");
    }

    @Override
    public String toString() {
        return "RxTestBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeStringList(this.list);
    }

    protected RxTestBean(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.list = in.createStringArrayList();
    }

    public static final Creator<RxTestBean> CREATOR = new Creator<RxTestBean>() {
        @Override
        public RxTestBean createFromParcel(Parcel source) {
            return new RxTestBean(source);
        }

        @Override
        public RxTestBean[] newArray(int size) {
            return new RxTestBean[size];
        }
    };
}
