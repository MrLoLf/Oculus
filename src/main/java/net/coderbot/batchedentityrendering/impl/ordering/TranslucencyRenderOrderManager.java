package net.coderbot.batchedentityrendering.impl.ordering;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;

import net.coderbot.batchedentityrendering.impl.BlendingStateHolder;
import net.coderbot.batchedentityrendering.impl.TransparencyType;
import net.coderbot.batchedentityrendering.impl.WrappableRenderType;
import net.minecraft.client.renderer.RenderType;

public class TranslucencyRenderOrderManager implements RenderOrderManager {
    private final EnumMap<TransparencyType, LinkedHashSet<RenderType>> renderTypes;

    public TranslucencyRenderOrderManager() {
        renderTypes = new EnumMap<>(TransparencyType.class);

        for (TransparencyType type : TransparencyType.values()) {
            renderTypes.put(type, new LinkedHashSet<>());
        }
    }

    private static TransparencyType getTransparencyType(RenderType type) {
        while (type instanceof WrappableRenderType) {
            type = ((WrappableRenderType) type).unwrap();
        }

        if (type instanceof BlendingStateHolder) {
            return ((BlendingStateHolder) type).getTransparencyType();
        }

        // Default to "generally transparent" if we can't figure it out.
        return TransparencyType.GENERAL_TRANSPARENT;
    }

    public void begin(RenderType type) {
        renderTypes.get(getTransparencyType(type)).add(type);
    }

    public void startGroup() {
        // no-op
    }

    public boolean maybeStartGroup() {
        // no-op
        return false;
    }

    public void endGroup() {
        // no-op
    }

    @Override
    public void reset() {
        renderTypes.forEach((type, set) -> {
            set.clear();
        });
    }

    public Iterable<RenderType> getRenderOrder() {
        int layerCount = 0;

        for (LinkedHashSet<RenderType> set : renderTypes.values()) {
            layerCount += set.size();
        }

        List<RenderType> allRenderTypes = new ArrayList<>(layerCount);

        for (LinkedHashSet<RenderType> set : renderTypes.values()) {
            allRenderTypes.addAll(set);
        }

        return allRenderTypes;
    }
}
