package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.PartTrainEntity;

import java.util.List;

/**
 * @author Kuznetsovka 24.07.2023
 */
public interface MapTrainRepository extends JpaRepository<MapTrainEntity, Long> {
    @Query("select map.orderWagon from MapTrainEntity map inner join map.road r where r.number = :number order by map.orderWagon.orderWagon")
    List<PartTrainEntity> getSetPartTrainByRoadNumber(Long number);

    @Query("select map from MapTrainEntity map inner join map.orderWagon ow where ow.orderWagon in (:orders)")
    List<MapTrainEntity> getRoadByOrderWagonIn(List<Long> orders);

    @Query("select min(ow.orderWagon) from MapTrainEntity map inner join map.orderWagon ow inner join map.road r where r.number = :number")
    Long getMinOrder(Long number);

    @Query("select max(ow.orderWagon) from MapTrainEntity map inner join map.orderWagon ow  inner join map.road r where r.number = :number")
    Long getMaxOrder(Long number);

    @Query("select map from MapTrainEntity map " +
            "inner join map.orderWagon.wagon w where w.wagonInfo.number in (:wagonNumbers) order by map.orderWagon.orderWagon")
    List<MapTrainEntity> getOrderByWagonNumber(List<Long> wagonNumbers);
}
