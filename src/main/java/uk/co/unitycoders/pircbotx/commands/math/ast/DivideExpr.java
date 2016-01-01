package uk.co.unitycoders.pircbotx.commands.math.ast;

/**
 * Copyright leon
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author leon on 16-1-1
 */
public class DivideExpr extends Expr{
    public Expr left;
    public Expr right;

    public DivideExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "DivideExpr{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
