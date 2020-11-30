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
package esa.commons.function;

import esa.commons.Checks;
import esa.commons.ExceptionUtils;

import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

/**
 * Represents a function that produces an long-valued result, which allows user to throw a checked exception.
 *
 * @param <T> the type of the result of the function
 * @see ToLongFunction
 */
@FunctionalInterface
public interface ThrowingToLongFunction<T> {

    /**
     * Wraps a {@link ThrowingToLongFunction} to {@link ToLongFunction} which will rethrow the error thrown by the given
     * {@link ThrowingToLongFunction}
     *
     * @param f   target to wrap
     * @param <T> the type of the result of the function
     *
     * @return transferred
     */
    static <T> ToLongFunction<T> rethrow(ThrowingToLongFunction<T> f) {
        Checks.checkNotNull(f);
        return (t) -> {
            try {
                return f.applyAsLong(t);
            } catch (Throwable e) {
                ExceptionUtils.throwException(e);
                // never reach this statement
                return -1L;
            }
        };
    }

    /**
     * Wraps a {@link ThrowingToLongFunction} to {@link ToLongFunction} which will use the given value of {@code
     * onFailure} as the result of the function when an error is thrown by the given {@link ThrowingToLongFunction}
     *
     * @param f         target to wrap
     * @param onFailure onFailure
     * @param <T>       the type of the result of the function
     *
     * @return transferred
     */
    static <T> ToLongFunction<T> onFailure(ThrowingToLongFunction<T> f,
                                           long onFailure) {
        Checks.checkNotNull(f);
        return (t) -> {
            try {
                return f.applyAsLong(t);
            } catch (Throwable e) {
                return onFailure;
            }
        };
    }

    /**
     * @see #failover(ThrowingToLongFunction, ToLongBiFunction)
     */
    static <T> ToLongFunction<T> failover(ThrowingToLongFunction<T> f,
                                          ToLongFunction<Throwable> failover) {
        return failover(f, (t, e) -> failover.applyAsLong(e));
    }

    /**
     * Wraps a {@link ThrowingToLongFunction} to {@link ToLongFunction} which will handle the error thrown by the given
     * {@link ThrowingToLongFunction} with the given {@code failover} operation of {@link ToLongBiFunction}
     *
     * @param f        target to wrap
     * @param failover failover
     * @param <T>      the type of the result of the function
     *
     * @return transferred
     */
    static <T> ToLongFunction<T> failover(ThrowingToLongFunction<T> f,
                                          ToLongBiFunction<T, Throwable> failover) {
        Checks.checkNotNull(f);
        Checks.checkNotNull(failover);
        return (t) -> {
            try {
                return f.applyAsLong(t);
            } catch (Throwable e) {
                return failover.applyAsLong(t, e);
            }
        };
    }

    /**
     * @see ToLongFunction#applyAsLong(Object)
     */
    long applyAsLong(T value) throws Throwable;

}
