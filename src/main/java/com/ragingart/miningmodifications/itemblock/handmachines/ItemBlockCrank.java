package com.ragingart.miningmodifications.itemblock.handmachines;

import com.ragingart.miningmodifications.generics.BlockMachinePP;
import com.ragingart.miningmodifications.generics.ItemBlockMM;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlockCrank extends ItemBlockMM {
    public ItemBlockCrank(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        return world.getBlockState(pos.offset(side)).getBlock() instanceof BlockMachinePP && super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
    }
}
