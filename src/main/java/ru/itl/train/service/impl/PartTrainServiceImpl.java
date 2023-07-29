package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.PartTrain;
import ru.itl.train.entity.PartTrainEntity;
import ru.itl.train.repository.PartTrainRepository;
import ru.itl.train.service.MapperService;
import ru.itl.train.service.PartTrainService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class PartTrainServiceImpl implements PartTrainService {

    private final PartTrainRepository repository;

    private final MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PartTrain update(PartTrain partTrain) {
        PartTrainEntity entity = mapper.partTrainEntityFromDto(partTrain);
        PartTrainEntity savedPartTrain = repository.save(entity);
        return mapper.partTrainDtoFromEntity(savedPartTrain);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String delete(Long order, Long wagonNumber) {
        if (wagonNumber != null && order != null && repository.existsByOrderAndWagon_WagonInfo_Number(order, wagonNumber)) {
            repository.findByOrderAndWagon_WagonInfo_Number(order, wagonNumber).ifPresent(repository::delete);

        }
        if (wagonNumber == null || order == null) {
            throw new IllegalArgumentException("Не корректные данные");
        }
        return String.join("-", order.toString(), wagonNumber.toString());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<PartTrain> getAll() {
        List<PartTrainEntity> partTrains = repository.findAll();
        return partTrains.stream().map(mapper::partTrainDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<PartTrain> getByOrderAndWagonNumber(Long order, Long wagonId) {
        Optional<PartTrainEntity> partTrain = repository.findByOrderAndWagon_WagonInfo_Number(order, wagonId);
        PartTrain dto = partTrain.map(mapper::partTrainDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PartTrain save(PartTrain partTrain) {
        if (partTrain.getOrder() != null && partTrain.getWagon() != null && partTrain.getWagon().getId() != null) {
            Optional<PartTrainEntity> savedEmployee = repository
                    .findByOrderAndWagon_WagonInfo_Number(partTrain.getOrder(), partTrain.getWagon().getId());
            if (savedEmployee.isPresent()) {
                String msg = String.format("Составной вагон под номером %d и номером вагона %d уже существует.",
                        partTrain.getOrder(), partTrain.getWagon().getNumber());
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
        }
        PartTrainEntity entity = mapper.partTrainEntityFromDto(partTrain);
        PartTrainEntity savedEntity = repository.save(entity);
        return mapper.partTrainDtoFromEntity(savedEntity);
    }
}
