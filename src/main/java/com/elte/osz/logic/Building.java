
package com.elte.osz.logic;

/**
 *
 * @author Tóth Ákos
 */
public enum Building {
    AjtosiDurerSor {
        @Override
        public String toString() {
            return "ajt";
        }
    },
    DeliTomb {
        @Override
        public String toString() {
            return "aaf";
        }
    },
    ELTEBotanikusKert {
        @Override
        public String toString() {
            return "bot";
        }
    },
    EszakiTomb {
        @Override
        public String toString() {
            return "aaa";
        }
    },
    IzabellaUtca {
        @Override
        public String toString() {
            return "iza";
        }
    },
    KazincyUtca {
        @Override
        public String toString() {
            return "aax";
        }
    },
    KemiaEpuletLagymanyos {
        @Override
        public String toString() {
            return "aae";
        }
    },
    MTA {
        @Override
        public String toString() {
            return "mta";
        }
    },
    TFKTestneveles {
        @Override
        public String toString() {
            return "aat";
        }
    },
    TrefortFEpulet {
        @Override
        public String toString() {
            return "aaz";
        }

    };

    public static String buildingToDispStr(Building building) {

        switch (building) {
            case AjtosiDurerSor:
                return "Ajtósi Dürer sor";
            case DeliTomb:
                return "Déli tömb";
            case ELTEBotanikusKert:
                return "ELTE Botanikus kert";
            case EszakiTomb:
                return "Északi tömb";
            case IzabellaUtca:
                return "Izabella utca";
            case KazincyUtca:
                return "Kazincy utca";
            case KemiaEpuletLagymanyos:
                return "Kémia épület(Lágymányos)";
            case MTA:
                return "MTA";
            case TFKTestneveles:
                return "TFK Testnevelés Tsz. termei";
            case TrefortFEpulet:
                return "Trefort F épület";
            default:
                return "";
        }

    }
;

};
