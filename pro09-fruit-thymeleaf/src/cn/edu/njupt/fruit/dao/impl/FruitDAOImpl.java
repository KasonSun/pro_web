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

}
