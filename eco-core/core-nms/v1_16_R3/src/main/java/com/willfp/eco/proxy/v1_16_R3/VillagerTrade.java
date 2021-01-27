package com.willfp.eco.proxy.v1_16_R3;

import com.willfp.eco.proxy.proxies.VillagerTradeProxy;
import com.willfp.eco.util.display.Display;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftMerchantRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class VillagerTrade implements VillagerTradeProxy {
    @Override
    public void displayTrade(@NotNull final MerchantRecipe merchantRecipe) {
        try {
            // Enables removing final modifier
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            // Bukkit MerchantRecipe result
            Field fResult = MerchantRecipe.class.getDeclaredField("result");
            fResult.setAccessible(true);
            ItemStack result = merchantRecipe.getResult();
            Display.displayAndFinalize(result);
            fResult.set(merchantRecipe, result);

            // Get NMS MerchantRecipe from CraftMerchantRecipe
            Field fHandle = CraftMerchantRecipe.class.getDeclaredField("handle");
            fHandle.setAccessible(true);
            net.minecraft.server.v1_16_R3.MerchantRecipe handle = (net.minecraft.server.v1_16_R3.MerchantRecipe) fHandle.get(merchantRecipe); // NMS Recipe
            modifiersField.setInt(fHandle, fHandle.getModifiers() & ~Modifier.FINAL); // Remove final

            Field fSelling = net.minecraft.server.v1_16_R3.MerchantRecipe.class.getDeclaredField("sellingItem");
            fSelling.setAccessible(true);
            modifiersField.setInt(fSelling, fSelling.getModifiers() & ~Modifier.FINAL);

            ItemStack selling = CraftItemStack.asBukkitCopy(handle.sellingItem);
            Display.displayAndFinalize(selling);

            fSelling.set(handle, CraftItemStack.asNMSCopy(selling));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}