package com.ragingart.miningmodifications.tileentity;

import com.ragingart.miningmodifications.block.BlockFluxField;
import com.ragingart.miningmodifications.generics.TileEntityMM;
import com.ragingart.miningmodifications.handler.ConfigHandler;
import com.ragingart.miningmodifications.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;

public class TileEntityPlatformBase extends TileEntityMM {
    private int animationTimer = 0;
    private boolean isExtracted = false;
    private ArrayList<Vec3> platform = new ArrayList<Vec3>();

    public int getAnimationTimer(){
        return this.animationTimer;
    }

    public boolean getExtractedState(){
        return isExtracted;
    }

    @Override
    public void updateEntity() {
        animationTimer++;
    }



    @Override
    public void writeToNBT(NBTTagCompound cmpd) {
        super.writeToNBT(cmpd);
        cmpd.setBoolean("isExtracted",getExtractedState());
        cmpd.setString("Platform",platformToString());
    }

    @Override
    public void readFromNBT(NBTTagCompound cmpd) {
        super.readFromNBT(cmpd);
        isExtracted = cmpd.getBoolean("isExtracted");
        platformFromString(cmpd.getString("Platform"));
    }


    public String platformToString(){
        String result=""+platform.size()+"_";

        for (Vec3 target : platform) {
            result += target.xCoord + ":" + target.yCoord + ":" + target.zCoord + "_";
        }

        return result;
    }

    public void platformFromString(String string){
        String parts[] = string.split("_");
        for (int i = 1; i < parts.length; i++) {
            String coords[] = parts[i].split(":");
            platform.add(Vec3.createVectorHelper(Double.parseDouble(coords[0]),Double.parseDouble(coords[1]),Double.parseDouble(coords[2])));
        }
    }

    public void togglePlatform(float x,float z, EntityPlayer entityPlayer){
        if(!worldObj.isRemote) {
            if(isExtracted){
                destroyPlatform();
            } else {
                float f1 = 1.0F/8.0F;
                float f2 = 2.0F/8.0F;
                float f3 = 4.0F/8.0F;
                float f4 = 7.0F/8.0F;
                if(isWithin(x,z,f1,f2)){
                    createPlatform(5);
                }
                else if(isWithin(x,z,f2,f3)){
                    createPlatform(10);
                }
                else if(isWithin(x,z,f3,f4)){
                    createPlatform(15);
                }else{
                    entityPlayer.addChatMessage(new ChatComponentText("Please hit one of the green boxes on the top side!"));
                }
            }
            this.markDirty();
        }
    }

    public void destroyPlatform(){
        for (int i = 1; i < platform.size(); i++) {
            Vec3 target = platform.get(i);
            int x = (int)target.xCoord;
            int y = (int)target.yCoord;
            int z = (int)target.zCoord;
            Block aBlock = worldObj.getBlock(x,y,z);
            if(aBlock instanceof BlockFluxField) {
                aBlock.breakBlock(worldObj, x, y, z, aBlock, 0);
            }

        }
        isExtracted=false;
        platform.clear();
    }

    private void createPlatform(int r){
        Vec3 center = Vec3.createVectorHelper(this.xCoord, this.yCoord, this.zCoord);
        platform.add(center);
        fillAdjAirBlocks(this.worldObj, center, r);
        isExtracted = true;
    }

    public void fillAdjAirBlocks(World world,Vec3 center, int r){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = (int)center.xCoord+i;
                int y = (int)center.yCoord;
                int z = (int)center.zCoord+j;
                if(world.getBlock(x,y,z).isReplaceable(world,x,y,z)){
                    Vec3 target = Vec3.createVectorHelper(x,y,z);
                    if(platform.get(0).distanceTo(target)< Math.min(ConfigHandler.maxPlatformRadius,r) && platform.size()<= ConfigHandler.maxPlatformSize) {
                        world.setBlock(x, y, z, ModBlocks.FluxField);
                        platform.add(target);
                        fillAdjAirBlocks(world,target,r);
                    }
                }
            }
        }
    }

    public boolean isWithin(float x, float z, float lower, float upper){
        return x>lower && x<upper && z>lower && z<upper;
    }

}
