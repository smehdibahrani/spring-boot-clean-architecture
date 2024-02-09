package ir.mehdi.mycleanarch.infrastructure.common.utils;


import ir.mehdi.mycleanarch.domain.models.Identity;

public final class IdConverter {

    public static Long convertId(Identity id) {
        if (id != null && id.getNumber() != Long.MIN_VALUE) {
            return id.getNumber();
        }

        return null;
    }
}
