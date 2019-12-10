package com.amir.service.dictionary;

import com.amir.mapper.dictionary.DictionaryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    public List<String> getList(Integer type) {

        return dictionaryMapper.getList(type);
    }

    public List<String> getListByNameAndType(String name, Integer type) {
        return dictionaryMapper.getListByNameAndType(name, type);
    }
}
