package com.ragingart.miningmodifications.generics;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

/**
 * Created by XtraX on 18.10.2014.
 */
public abstract class BlockMachinePP extends  BlockMM implements ITileEntityProvider {
    public BlockMachinePP(String aName,Class<? extends ItemBlockMM> aClass){
        super(Material.wood,aName,aClass);
    }
}
