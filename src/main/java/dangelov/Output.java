package dangelov;

import javafx.scene.paint.Color;

public class Output {
    private Output.ResultCode resultCode;
    private String message;
    private Color color;

    public Color getColor() {
        return this.color;
    }

    public Output(Output.ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
        this.color = resultCode == Output.ResultCode.FOUND?Color.GREEN:Color.RED;
    }

    public Output.ResultCode getResultCode() {
        return this.resultCode;
    }

    public String getMessage() {
        return this.message;
    }

    public enum ResultCode {
        FOUND,
        NOT_FOUND;
    }
}
