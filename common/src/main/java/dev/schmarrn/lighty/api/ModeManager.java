// Copyright 2022-2023 The Lighty contributors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dev.schmarrn.lighty.api;

import dev.schmarrn.lighty.ModeLoader;
import dev.schmarrn.lighty.ui.ModeButtonRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Used for registering your LightyModes.
 */
public class ModeManager {
    /**
     * Registers a Lighty Mode and adds a Button to enable the mode to the ModeSwitcherScreen.
     * For the Button, you need to specify `modeSwitcher.{id.getNamespace}.{id.getPath}` for the
     * Button Name, and `modeSwitcher.{id.getNamespace}.{id.getPath}.tooltip` for the Tooltip of
     * the Button.
     *
     * If on Fabric: use the LightyModesRegistration EntryPoint
     * If on Forge: call during FMLClientSetupEvent and enqueue work
     * @param id Used to generate the translatable text resource locations
     * @param mode Your LightyMode to be registered
     */
    public static void registerMode(ResourceLocation id, LightyMode mode) {
        ModeLoader.put(id, mode);

        ModeButtonRegister.addButton(
                Component.translatable("modeSwitcher." + id.getNamespace() + "." + id.getPath()),
                Component.translatable("modeSwitcher." + id.getNamespace() + "." + id.getPath() + ".tooltip"), button -> ModeLoader.loadMode(id)
        );
    }

    private ModeManager() {}
}
