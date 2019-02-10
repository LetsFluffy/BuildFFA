package de.letsfluffy.plorax.buildffa.buildblocks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.buildblocks
 * Class created: 2019-02-07, 15:53
 */
@Getter
public class BuildBlock {

    private Block block;
    private final BuildBlocks buildBlocks;
    private final boolean changeBlock;
    @Setter
    private int time = 8*20;

    public BuildBlock(Block block, BuildBlocks buildBlocks, boolean changeBlock) {
        this.block = block;
        this.buildBlocks = buildBlocks;
        this.changeBlock = changeBlock;
    }

    public void subtractTime() {
        setTime(getTime()-1);
    }


}
