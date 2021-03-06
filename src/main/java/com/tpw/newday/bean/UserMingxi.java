package com.tpw.newday.bean;

import com.tpw.newday.utils.GsonUtils;
import com.tpw.newday.utils.MyDateUtil;

import javax.persistence.*;
import java.util.Date;

@Table(name = "fanli_mingxi_new")
@Entity
public class UserMingxi {
    @Id  //ID代表是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //按照主键自增
    private Integer id;


    private Integer uid;

    private Integer team_uid;

    private Integer trade_uid;

    private String trade_id_former;

    private Date create_time;

    private String shijian;
    
    private Integer money;

    private Integer relate_id;

    public Integer getRelate_id() {
        return relate_id;
    }

    public void setRelate_id(Integer relate_id) {
        this.relate_id = relate_id;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTeam_uid() {
        return team_uid;
    }

    public void setTeam_uid(Integer team_uid) {
        this.team_uid = team_uid;
    }

    public Integer getTrade_uid() {
        return trade_uid;
    }

    public void setTrade_uid(Integer trade_uid) {
        this.trade_uid = trade_uid;
    }

    public String getTrade_id_former() {
        return trade_id_former;
    }

    public void setTrade_id_former(String trade_id_former) {
        this.trade_id_former = trade_id_former;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
    

    public String toString()
    {
//        return this.getId() + " " + this.getUid()+ " "+ this.getTeam_uid()+ " "+ this.getTrade_uid()+ " "
//                + this.getTrade_id_former()+ " "+ MyDateUtil.formatDate(this.getCreate_time(),null)+ " "+ this.getShijian()+ " "
//                + this.getMoney()+ " "+ this.getRelate_id();
        return GsonUtils.getGson().toJson(this);
    }


}