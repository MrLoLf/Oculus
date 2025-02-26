/*
 * Anarres C Preprocessor
 * Copyright (c) 2007-2015, Shevek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.anarres.cpp;

import java.io.IOException;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

/**
 * An extremely lightweight virtual file interface.
 */
public interface VirtualFile {

    // public String getParent();
    public boolean isFile();

    @Nonnull
    public String getPath();

    @Nonnull
    public String getName();

    @CheckForNull
    public VirtualFile getParentFile();

    @Nonnull
    public VirtualFile getChildFile(String name);

    @Nonnull
    public Source getSource() throws IOException;
}
