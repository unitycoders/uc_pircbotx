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
public class FuncExpr extends Expr {
    public String funcName;
    public Expr epxr;

    public FuncExpr(String funcName,Expr epxr) {
        this.epxr = epxr;
        this.funcName = funcName;
    }

    public double eval() {
        String funcNameNorm = funcName.toUpperCase();
        switch (funcNameNorm) {
            case "SIN":
                return Math.sin(epxr.eval());
            case "COS":
                return Math.cos(epxr.eval());
            case "TAN":
                return Math.tan(epxr.eval());
            case "RAD":
                return Math.toRadians(epxr.eval());
            case "DEG":
                return Math.toDegrees(epxr.eval());
            default:
                System.err.println("unknown function, " + funcName);
                return -1;
        }
    }

    @Override
    public String toString() {
        return "FuncExpr{" +
                "funcName='" + funcName + '\'' +
                ", epxr=" + epxr +
                '}';
    }
}
