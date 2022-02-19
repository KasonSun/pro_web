package cn.edu.njupt.fruit.dao.impl;

import cn.edu.njupt.fruit.dao.FruitDAO;
import cn.edu.njupt.fruit.pojo.Fruit;
import cn.edu.njupt.myssm.basedao.BaseDAO;

import java.util.List;

//注意此处继承BaseDAO的泛型一定不要忘记，教训
public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {

    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return super.load("select * from t_fruit where fid = ? ", fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname=?, price=?, fcount=?, remark=? where fid=?";
        super.executeUpdate(sql,fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(),fruit.getFid());
    }

    @Override
    public void delFruit(Integer fid) {
        super.executeUpdate("delete from t_fruit where fid=?", fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit (fid, fname, price, fcount, remark) values (0,?,?,?,?)";//(fid, fname, price, fcount, remark)完整记录可以省略
        super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }
}
