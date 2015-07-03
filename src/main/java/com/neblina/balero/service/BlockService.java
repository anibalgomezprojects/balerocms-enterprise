/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia Pública GNU versión 3 o superior; vea LICENSE.txt
 */

package com.neblina.balero.service;

import com.neblina.balero.domain.Block;
import com.neblina.balero.service.repository.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    private final Logger log = LoggerFactory.getLogger(BlockService.class);

    @Autowired
    private BlockRepository blockRepository;

    public void createBlock(String name,
                            String content,
                            String code) {
        Block block = new Block();
        block.setName(name);
        block.setContent(content);
        block.setCode(code);
        blockRepository.save(block);
    }

    public void saveBlock(Long id,
                          String name,
                          String content,
                          String code) {
        Block block = blockRepository.findOneById(id);
        block.setName(name);
        block.setContent(content);
        block.setCode(code);
        blockRepository.save(block);
    }

}
