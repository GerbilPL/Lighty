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

package dev.schmarrn.lighty.ui;

import com.mojang.serialization.Codec;
import dev.schmarrn.lighty.config.Config;
import dev.schmarrn.lighty.event.Compute;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SettingsScreen extends Screen {
    public SettingsScreen() {
        super(Component.translatable("settings.lighty.title"));
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        OptionsList list = new OptionsList(this.minecraft, this.width, this.height - 32, 32, 25);

        list.addBig(new OptionInstance<>(
                "lighty.options.overlay_distance",
                object -> Tooltip.create(Component.translatable("lighty.options.overlay_distance.tooltip", object)),
                (component, integer) -> Options.genericValueLabel(component, Component.literal(integer.toString())),
                new OptionInstance.IntRange(1, 32),
                Codec.intRange(1, 32),
                Config.OVERLAY_DISTANCE.getValue(),
                Config.OVERLAY_DISTANCE::setValue
        ));

        list.addBig(new OptionInstance<>(
                "lighty.options.overlay_brightness",
                object -> Tooltip.create(Component.translatable("lighty.options.overlay_brightness.tooltip", object)),
                (component, integer) -> Options.genericValueLabel(component, Component.literal(integer.toString())),
                new OptionInstance.IntRange(0, 15),
                Codec.intRange(0, 15),
                Config.OVERLAY_BRIGHTNESS.getValue(),
                Config.OVERLAY_BRIGHTNESS::setValue
        ));

        list.addSmall(
                new OptionInstance[]{
                        new OptionInstance<>(
                                "lighty.options.block_threshold",
                                object -> Tooltip.create(Component.translatable("lighty.options.block_threshold.tooltip", object)),
                                (component, integer) -> Options.genericValueLabel(component, Component.literal(integer.toString())),
                                new OptionInstance.IntRange(0, 15),
                                Codec.intRange(0, 15),
                                Config.BLOCK_THRESHOLD.getValue(),
                                Config.BLOCK_THRESHOLD::setValue
                        ),
                        new OptionInstance<>(
                                "lighty.options.sky_threshold",
                                object -> Tooltip.create(Component.translatable("lighty.options.sky_threshold.tooltip", object)),
                                (component, integer) -> Options.genericValueLabel(component, Component.literal(integer.toString())),
                                new OptionInstance.IntRange(0, 15),
                                Codec.intRange(0, 15),
                                Config.SKY_THRESHOLD.getValue(),
                                Config.SKY_THRESHOLD::setValue
                        ),
                        OptionInstance.createBoolean(
                                "lighty.options.show_safe",
                                object -> Tooltip.create(Component.translatable("lighty.options.show_safe.tooltip", object)),
                                Config.SHOW_SAFE.getValue(),
                                Config.SHOW_SAFE::setValue
                        )
                }
        );

        this.addRenderableWidget(list);
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderDirtBackground(guiGraphics);
    }

    @Override
    public void onClose() {
        Compute.clear();
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
