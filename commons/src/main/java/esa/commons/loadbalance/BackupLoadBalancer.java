/*
 * Copyright 2020 OPPO ESA Stack Project
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
package esa.commons.loadbalance;

import java.util.List;

public abstract class BackupLoadBalancer<T> implements LoadBalancer<T> {

    @Override
    public T select(List<T> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }
        for (T element : elements) {
            if (isMaster(element)) {
                return element;
            }
        }
        return null;
    }

    protected abstract boolean isMaster(T element);
}
