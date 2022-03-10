package com.hebeu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * client
 * @author 
 */
@Data
public class ClientModel implements Serializable {
    /**
     * 客户信息唯一标识符
     */
    private Integer id;

    /**
     * 客户账户名称
     */
    private String uname;

    /**
     * 客户账户密码
     */
    private String passwd;

    /**
     * 客户余额
     */
    private BigDecimal money;

    /**
     * 客户会员积分
     */
    private BigDecimal point;

    /**
     * 客户默认地址
     */
    private String defadd;

    /**
     * 客户头像
     */
    private String pic;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ClientModel other = (ClientModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUname() == null ? other.getUname() == null : this.getUname().equals(other.getUname()))
            && (this.getPasswd() == null ? other.getPasswd() == null : this.getPasswd().equals(other.getPasswd()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getPoint() == null ? other.getPoint() == null : this.getPoint().equals(other.getPoint()))
            && (this.getDefadd() == null ? other.getDefadd() == null : this.getDefadd().equals(other.getDefadd()))
            && (this.getPic() == null ? other.getPic() == null : this.getPic().equals(other.getPic()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUname() == null) ? 0 : getUname().hashCode());
        result = prime * result + ((getPasswd() == null) ? 0 : getPasswd().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getPoint() == null) ? 0 : getPoint().hashCode());
        result = prime * result + ((getDefadd() == null) ? 0 : getDefadd().hashCode());
        result = prime * result + ((getPic() == null) ? 0 : getPic().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uname=").append(uname);
        sb.append(", passwd=").append(passwd);
        sb.append(", money=").append(money);
        sb.append(", point=").append(point);
        sb.append(", defadd=").append(defadd);
        sb.append(", pic=").append(pic);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}