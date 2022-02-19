package cn.edu.njupt.fruit.dao;

import cn.edu.njupt.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //查询库存列表
    List<Fruit> getFruitList();

    //新增库存
    boolean addFruit(Fruit fruit);

    //修改库存
    boolean updateFruit(Fruit fruit);

    //根据名称查询特定库存
    Fruit getFruitByFname(String name);

    //删除特定的库存记录
    boolean delFruit(String name);
}
