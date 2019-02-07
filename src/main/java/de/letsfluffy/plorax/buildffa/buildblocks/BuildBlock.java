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
    @Setter
    private int time = 8;

    public BuildBlock(Block block, BuildBlocks buildBlocks) {
        this.block = block;
        this.buildBlocks = buildBlocks;
    }

    public void subtractTime() {
        setTime(getTime()-1);
    }


}
