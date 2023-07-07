class RefreshTokenExpiredError extends Error {
    constructor(message) {
        super(message);
        this.name = "RefreshTokenExpiredError";
    }
}

export default RefreshTokenExpiredError;
