/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.resource.conditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackProfile;

import net.fabricmc.fabric.impl.resource.conditions.OverlayConditionsMetadata;

@Mixin(ResourcePackProfile.class)
public class ResourcePackProfileMixin {
	@ModifyVariable(method = "loadMetadata", at = @At("STORE"))
	private static List<String> fabric_applyOverlayConditions(List<String> overlays, @Local ResourcePack resourcePack) throws IOException {
		List<String> appliedOverlays = new ArrayList<>(overlays);
		OverlayConditionsMetadata overlayConditionsMetadata = resourcePack.parseMetadata(OverlayConditionsMetadata.SERIALIZER);

		if (overlayConditionsMetadata != null) {
			appliedOverlays.addAll(overlayConditionsMetadata.getAppliedOverlays());
		}

		return appliedOverlays;
	}
}
