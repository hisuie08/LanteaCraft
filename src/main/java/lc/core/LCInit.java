package lc.core;

import lc.api.components.RecipeType;
import lc.api.defs.IDefinitionReference;
import lc.api.init.Blocks;
import lc.api.init.Items;
import lc.api.init.Recipes;
import lc.api.init.Structures;
import lc.api.world.OreType;
import lc.blocks.BlockDecorative;
import lc.blocks.BlockLanteaAlloy;
import lc.blocks.BlockLanteaOre;
import lc.blocks.BlockStargateBase;
import lc.blocks.BlockStargateRing;
import lc.common.impl.registry.DefinitionReference;
import lc.common.impl.registry.DefinitionWrapperProvider;
import lc.common.impl.registry.RecipeProxy;
import lc.common.impl.registry.SimpleRecipeDefinition;
import lc.common.impl.registry.StructureDefinition;
import lc.generation.AbydosPyramid;
import lc.generation.AbydosPyramid.AbydosPyramidFeature;
import lc.items.ItemCraftingReagent;
import lc.items.ItemDecorator;
import lc.items.ItemGlasses;
import lc.items.ItemLanteaAlloyIngot;
import lc.items.ItemLanteaOre;
import lc.recipe.DecoratorSetterRecipe;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Registration initializer
 *
 * @author AfterLifeLochie
 *
 */
public class LCInit {

	/**
	 * Called during pre-initialization
	 *
	 * @param runtime
	 *            The runtime
	 * @param event
	 *            The original FML event
	 */
	public void preinit(LCRuntime runtime, FMLPreInitializationEvent event) {
		// TODO: Auto-generated method stub
	}

	/**
	 * Called during initialization
	 *
	 * @param runtime
	 *            The runtime
	 * @param event
	 *            The original FML event
	 */
	public void init(LCRuntime runtime, FMLInitializationEvent event) {
		Blocks blocks = runtime.blocks();
		Items items = runtime.items();
		Recipes recipes = runtime.recipes();
		Structures structures = runtime.structures();

		blocks.stargateRingBlock = DefinitionWrapperProvider.provide(BlockStargateRing.class);
		blocks.stargateBaseBlock = DefinitionWrapperProvider.provide(BlockStargateBase.class);
		blocks.lanteaOreBlock = DefinitionWrapperProvider.provide(BlockLanteaOre.class);
		blocks.lanteaAlloyBlock = DefinitionWrapperProvider.provide(BlockLanteaAlloy.class);

		blocks.lanteaDecorBlock = DefinitionWrapperProvider.provide(BlockDecorative.class);

		items.glasses = DefinitionWrapperProvider.provide(ItemGlasses.class);
		items.lanteaOreItem = DefinitionWrapperProvider.provide(ItemLanteaOre.class);
		items.lanteaAlloyItem = DefinitionWrapperProvider.provide(ItemLanteaAlloyIngot.class);
		items.lanteaCraftingItem = DefinitionWrapperProvider.provide(ItemCraftingReagent.class);
		items.lanteaDecoratorTool = DefinitionWrapperProvider.provide(ItemDecorator.class);

		IDefinitionReference ringBlock = blocks.stargateRingBlock.ref();
		IDefinitionReference chevronBlock = blocks.stargateRingBlock.ref().pushAll(1, 1);
		IDefinitionReference baseBlock = blocks.stargateBaseBlock.ref();
		IDefinitionReference naquadah = items.lanteaOreItem.ref().pushAll(1, OreType.NAQUADAH.ordinal());
		IDefinitionReference trinium = items.lanteaOreItem.ref().pushAll(1, OreType.TRINIUM.ordinal());
		IDefinitionReference naquadahIngot = items.lanteaAlloyItem.ref().pushAll(1, OreType.NAQUADAH.ordinal());
		IDefinitionReference triniumIngot = new DefinitionReference(items.lanteaAlloyItem, 1, OreType.TRINIUM.ordinal());
		IDefinitionReference naquadahAlloyBlock = blocks.lanteaAlloyBlock.ref().pushAll(1, OreType.NAQUADAH.ordinal());
		IDefinitionReference triniumAlloyBlock = blocks.lanteaAlloyBlock.ref().pushAll(1, OreType.TRINIUM.ordinal());
		items.lanteaCraftingItem.ref().pushAll(1, ItemCraftingReagent.ReagentList.BLANKCRYSTAL.ordinal());
		IDefinitionReference coreCrystal = items.lanteaCraftingItem.ref().pushAll(1,
				ItemCraftingReagent.ReagentList.CORECRYSTAL.ordinal());
		items.lanteaCraftingItem.ref().pushAll(1, ItemCraftingReagent.ReagentList.CONTROLCRYSTAL.ordinal());

		ItemStack cSandstone = new ItemStack(net.minecraft.init.Blocks.sandstone, 1, 1);
		ItemStack ironIngot = new ItemStack(net.minecraft.init.Items.iron_ingot, 1);
		ItemStack redstone = new ItemStack(net.minecraft.init.Items.redstone, 1);
		ItemStack eyeOfEnder = new ItemStack(net.minecraft.init.Items.ender_eye, 1);
		ItemStack glowstone = new ItemStack(net.minecraft.init.Items.glowstone_dust, 1);
		ItemStack enderPearl = new ItemStack(net.minecraft.init.Items.ender_pearl, 1);

		recipes.decorSetterRecipe = new RecipeProxy("decor_editor", RecipeType.PROXY, DecoratorSetterRecipe.class);
		runtime.registries().recipes().addRecipe(recipes.decorSetterRecipe);

		recipes.stargateBase = new SimpleRecipeDefinition("stargate_base", RecipeType.SHAPED, baseBlock, "010232454",
				cSandstone, redstone, naquadahIngot, eyeOfEnder, ironIngot, coreCrystal);
		recipes.stargateRing = new SimpleRecipeDefinition("stargate_ring", RecipeType.SHAPED, ringBlock, "010222000",
				ironIngot, cSandstone, naquadahIngot);
		recipes.stargateChevron = new SimpleRecipeDefinition("stargate_chevron", RecipeType.SHAPED, chevronBlock,
				"010232454", cSandstone, glowstone, naquadahIngot, enderPearl, ironIngot, redstone);
		runtime.registries().recipes().addRecipe(recipes.stargateBase);
		runtime.registries().recipes().addRecipe(recipes.stargateRing);
		runtime.registries().recipes().addRecipe(recipes.stargateChevron);

		recipes.naquadahIngot = new SimpleRecipeDefinition("naquadah_ingot", RecipeType.SHAPELESS, naquadahIngot, "01",
				ironIngot, naquadah);
		recipes.triniumIngot = new SimpleRecipeDefinition("trinium_ingot", RecipeType.SHAPELESS, triniumIngot, "01",
				ironIngot, trinium);
		runtime.registries().recipes().addRecipe(recipes.naquadahIngot);
		runtime.registries().recipes().addRecipe(recipes.triniumIngot);

		recipes.naquadahAlloyBlock = new SimpleRecipeDefinition("naquadah_alloy_block", RecipeType.SHAPED,
				naquadahAlloyBlock, "000000000", naquadahIngot);
		recipes.naquadahAlloyToIngots = new SimpleRecipeDefinition("naquadah_alloy_to_ingot", RecipeType.SHAPELESS,
				naquadahIngot.copy().push(0, 9), "0", naquadahAlloyBlock);
		runtime.registries().recipes().addRecipe(recipes.naquadahAlloyBlock);
		runtime.registries().recipes().addRecipe(recipes.naquadahAlloyToIngots);

		recipes.triniumAlloyBlock = new SimpleRecipeDefinition("trinium_alloy_block", RecipeType.SHAPED,
				triniumAlloyBlock, "000000000", triniumIngot);
		recipes.triniumAlloyToIngots = new SimpleRecipeDefinition("trinium_alloy_to_ingot", RecipeType.SHAPELESS,
				triniumIngot.copy().push(0, 9), "0", triniumAlloyBlock);
		runtime.registries().recipes().addRecipe(recipes.triniumAlloyBlock);
		runtime.registries().recipes().addRecipe(recipes.triniumAlloyToIngots);

		structures.scatteredAbydosPyramid = new StructureDefinition("AbydosPyramid", AbydosPyramid.class).addComp(
				"AbydosPyramid", AbydosPyramidFeature.class);
		runtime.registries().structures().register(structures.scatteredAbydosPyramid);

	}

	/**
	 * Called during post-initialization
	 *
	 * @param runtime
	 *            The runtime
	 * @param event
	 *            The original FML event
	 */
	public void postinit(LCRuntime runtime, FMLPostInitializationEvent event) {
		// TODO: Auto-generated method stub
	}

}
