--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2025-04-23 13:43:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 19278)
-- Name: proyecto; Type: SCHEMA; Schema: -; Owner: proyecto
--

CREATE SCHEMA proyecto;


ALTER SCHEMA proyecto OWNER TO proyecto;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 233 (class 1259 OID 19395)
-- Name: actividades; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.actividades (
    id_actividad integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    fecha timestamp without time zone NOT NULL,
    id_tipo_actividad integer NOT NULL,
    cupo integer NOT NULL,
    can_inscriptos integer DEFAULT 0 NOT NULL,
    can_cancelados integer DEFAULT 0,
    estado boolean DEFAULT true NOT NULL,
    CONSTRAINT actividades_can_cancelados_check CHECK ((can_cancelados >= 0)),
    CONSTRAINT actividades_check CHECK (((can_inscriptos >= 0) AND (can_inscriptos <= cupo))),
    CONSTRAINT actividades_cupo_check CHECK ((cupo >= 0))
);


ALTER TABLE proyecto.actividades OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 19394)
-- Name: actividades_id_actividad_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.actividades_id_actividad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.actividades_id_actividad_seq OWNER TO postgres;

--
-- TOC entry 5021 (class 0 OID 0)
-- Dependencies: 232
-- Name: actividades_id_actividad_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.actividades_id_actividad_seq OWNED BY proyecto.actividades.id_actividad;


--
-- TOC entry 251 (class 1259 OID 19514)
-- Name: auditorias; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.auditorias (
    id_auditoria integer NOT NULL,
    fec_hora timestamp without time zone NOT NULL,
    terminal character varying(50) NOT NULL,
    id_usuario integer NOT NULL,
    operacion character varying(50) NOT NULL
);


ALTER TABLE proyecto.auditorias OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 19513)
-- Name: auditorias_id_auditoria_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.auditorias_id_auditoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.auditorias_id_auditoria_seq OWNER TO postgres;

--
-- TOC entry 5024 (class 0 OID 0)
-- Dependencies: 250
-- Name: auditorias_id_auditoria_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.auditorias_id_auditoria_seq OWNED BY proyecto.auditorias.id_auditoria;


--
-- TOC entry 243 (class 1259 OID 19472)
-- Name: cancelaciones; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.cancelaciones (
    id_cancelacion integer NOT NULL,
    id_inscripcion integer NOT NULL,
    fec_cancelacion timestamp without time zone NOT NULL
);


ALTER TABLE proyecto.cancelaciones OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 19471)
-- Name: cancelaciones_id_cancelacion_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.cancelaciones_id_cancelacion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.cancelaciones_id_cancelacion_seq OWNER TO postgres;

--
-- TOC entry 5027 (class 0 OID 0)
-- Dependencies: 242
-- Name: cancelaciones_id_cancelacion_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.cancelaciones_id_cancelacion_seq OWNED BY proyecto.cancelaciones.id_cancelacion;


--
-- TOC entry 225 (class 1259 OID 19327)
-- Name: cate_socios; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.cate_socios (
    id_categoria_socio integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.cate_socios OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 19326)
-- Name: cate_socios_id_categoria_socio_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.cate_socios_id_categoria_socio_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.cate_socios_id_categoria_socio_seq OWNER TO postgres;

--
-- TOC entry 5030 (class 0 OID 0)
-- Dependencies: 224
-- Name: cate_socios_id_categoria_socio_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.cate_socios_id_categoria_socio_seq OWNED BY proyecto.cate_socios.id_categoria_socio;


--
-- TOC entry 219 (class 1259 OID 19292)
-- Name: departamentos; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.departamentos (
    id_departamento integer NOT NULL,
    nombre character varying(50) NOT NULL,
    CONSTRAINT departamentos_id_departamento_check CHECK (((id_departamento >= 1) AND (id_departamento <= 19)))
);


ALTER TABLE proyecto.departamentos OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 19291)
-- Name: departamentos_id_departamento_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.departamentos_id_departamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 19
    CACHE 1;


ALTER SEQUENCE proyecto.departamentos_id_departamento_seq OWNER TO postgres;

--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 218
-- Name: departamentos_id_departamento_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.departamentos_id_departamento_seq OWNED BY proyecto.departamentos.id_departamento;


--
-- TOC entry 223 (class 1259 OID 19314)
-- Name: domicilios; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.domicilios (
    id_domicilio integer NOT NULL,
    id_departamento integer NOT NULL,
    ciudad character varying(50) NOT NULL,
    calle character varying(50) NOT NULL,
    num_puerta integer NOT NULL,
    piso integer,
    apartamento character varying(10),
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.domicilios OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 19313)
-- Name: domicilios_id_domicilio_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.domicilios_id_domicilio_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.domicilios_id_domicilio_seq OWNER TO postgres;

--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 222
-- Name: domicilios_id_domicilio_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.domicilios_id_domicilio_seq OWNED BY proyecto.domicilios.id_domicilio;


--
-- TOC entry 235 (class 1259 OID 19413)
-- Name: espacios; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.espacios (
    id_espacio integer NOT NULL,
    nombre character varying(50) NOT NULL,
    ubicacion character varying(150) NOT NULL,
    capacidad integer NOT NULL,
    fec_vigencia_precios date NOT NULL,
    pre_reserva_socios numeric(10,2) NOT NULL,
    pre_reserva_no_socios numeric(10,2) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.espacios OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 19412)
-- Name: espacios_id_espacio_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.espacios_id_espacio_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.espacios_id_espacio_seq OWNER TO postgres;

--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 234
-- Name: espacios_id_espacio_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.espacios_id_espacio_seq OWNED BY proyecto.espacios.id_espacio;


--
-- TOC entry 249 (class 1259 OID 19504)
-- Name: funcionalidades; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.funcionalidades (
    id_funcionalidad integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.funcionalidades OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 19503)
-- Name: funcionalidades_id_funcionalidad_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.funcionalidades_id_funcionalidad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.funcionalidades_id_funcionalidad_seq OWNER TO postgres;

--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 248
-- Name: funcionalidades_id_funcionalidad_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.funcionalidades_id_funcionalidad_seq OWNED BY proyecto.funcionalidades.id_funcionalidad;


--
-- TOC entry 241 (class 1259 OID 19454)
-- Name: inscripciones; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.inscripciones (
    id_inscripcion integer NOT NULL,
    id_usuario integer NOT NULL,
    id_actividad integer NOT NULL,
    fec_inscripcion timestamp without time zone NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.inscripciones OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 19453)
-- Name: inscripciones_id_inscripcion_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.inscripciones_id_inscripcion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.inscripciones_id_inscripcion_seq OWNER TO postgres;

--
-- TOC entry 5045 (class 0 OID 0)
-- Dependencies: 240
-- Name: inscripciones_id_inscripcion_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.inscripciones_id_inscripcion_seq OWNED BY proyecto.inscripciones.id_inscripcion;


--
-- TOC entry 237 (class 1259 OID 19423)
-- Name: pagos; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.pagos (
    id_pago integer NOT NULL,
    fecha timestamp without time zone NOT NULL,
    monto numeric(10,2) NOT NULL,
    id_usuario integer NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.pagos OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 19422)
-- Name: pagos_id_pago_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.pagos_id_pago_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.pagos_id_pago_seq OWNER TO postgres;

--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 236
-- Name: pagos_id_pago_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.pagos_id_pago_seq OWNED BY proyecto.pagos.id_pago;


--
-- TOC entry 247 (class 1259 OID 19494)
-- Name: perfiles; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.perfiles (
    id_perfil integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.perfiles OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 19493)
-- Name: perfiles_id_perfil_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.perfiles_id_perfil_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.perfiles_id_perfil_seq OWNER TO postgres;

--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 246
-- Name: perfiles_id_perfil_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.perfiles_id_perfil_seq OWNED BY proyecto.perfiles.id_perfil;


--
-- TOC entry 239 (class 1259 OID 19436)
-- Name: reservas; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.reservas (
    id_reserva integer NOT NULL,
    id_usuario integer NOT NULL,
    fec_creada timestamp without time zone NOT NULL,
    fec_reserva date NOT NULL,
    hor_inicio time without time zone NOT NULL,
    hor_fin time without time zone NOT NULL,
    id_espacio integer NOT NULL,
    mon_senia numeric(10,2) NOT NULL,
    saldo numeric(10,2) NOT NULL,
    mon_total numeric(10,2) NOT NULL,
    can_personas integer NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.reservas OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 19435)
-- Name: reservas_id_reserva_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.reservas_id_reserva_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.reservas_id_reserva_seq OWNER TO postgres;

--
-- TOC entry 5054 (class 0 OID 0)
-- Dependencies: 238
-- Name: reservas_id_reserva_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.reservas_id_reserva_seq OWNED BY proyecto.reservas.id_reserva;


--
-- TOC entry 217 (class 1259 OID 19282)
-- Name: subcomisiones; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.subcomisiones (
    id_subcomision integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.subcomisiones OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 19281)
-- Name: subcomisiones_id_subcomision_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.subcomisiones_id_subcomision_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.subcomisiones_id_subcomision_seq OWNER TO postgres;

--
-- TOC entry 5057 (class 0 OID 0)
-- Dependencies: 216
-- Name: subcomisiones_id_subcomision_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.subcomisiones_id_subcomision_seq OWNED BY proyecto.subcomisiones.id_subcomision;


--
-- TOC entry 229 (class 1259 OID 19370)
-- Name: telefonos; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.telefonos (
    id_telefono integer NOT NULL,
    id_usuario integer NOT NULL,
    num_telefono integer NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.telefonos OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 19369)
-- Name: telefonos_id_telefono_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.telefonos_id_telefono_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.telefonos_id_telefono_seq OWNER TO postgres;

--
-- TOC entry 5060 (class 0 OID 0)
-- Dependencies: 228
-- Name: telefonos_id_telefono_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.telefonos_id_telefono_seq OWNED BY proyecto.telefonos.id_telefono;


--
-- TOC entry 231 (class 1259 OID 19385)
-- Name: tipo_actividades; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.tipo_actividades (
    id_tipo_actividad integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.tipo_actividades OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 19384)
-- Name: tipo_actividades_id_tipo_actividad_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.tipo_actividades_id_tipo_actividad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.tipo_actividades_id_tipo_actividad_seq OWNER TO postgres;

--
-- TOC entry 5063 (class 0 OID 0)
-- Dependencies: 230
-- Name: tipo_actividades_id_tipo_actividad_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.tipo_actividades_id_tipo_actividad_seq OWNED BY proyecto.tipo_actividades.id_tipo_actividad;


--
-- TOC entry 245 (class 1259 OID 19484)
-- Name: tipo_documentos; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.tipo_documentos (
    id_tipo_documento integer NOT NULL,
    nombre character varying(50) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.tipo_documentos OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 19483)
-- Name: tipo_documentos_id_tipo_documento_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.tipo_documentos_id_tipo_documento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.tipo_documentos_id_tipo_documento_seq OWNER TO postgres;

--
-- TOC entry 5066 (class 0 OID 0)
-- Dependencies: 244
-- Name: tipo_documentos_id_tipo_documento_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.tipo_documentos_id_tipo_documento_seq OWNED BY proyecto.tipo_documentos.id_tipo_documento;


--
-- TOC entry 221 (class 1259 OID 19304)
-- Name: tipo_usuarios; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.tipo_usuarios (
    id_tipo_usuario integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(150) NOT NULL,
    estado boolean DEFAULT true NOT NULL
);


ALTER TABLE proyecto.tipo_usuarios OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 19303)
-- Name: tipo_usuarios_id_tipo_usuario_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.tipo_usuarios_id_tipo_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.tipo_usuarios_id_tipo_usuario_seq OWNER TO postgres;

--
-- TOC entry 5069 (class 0 OID 0)
-- Dependencies: 220
-- Name: tipo_usuarios_id_tipo_usuario_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.tipo_usuarios_id_tipo_usuario_seq OWNED BY proyecto.tipo_usuarios.id_tipo_usuario;


--
-- TOC entry 227 (class 1259 OID 19337)
-- Name: usuarios; Type: TABLE; Schema: proyecto; Owner: postgres
--

CREATE TABLE proyecto.usuarios (
    id_usuario integer NOT NULL,
    pri_nombre character varying(50) NOT NULL,
    seg_nombre character varying(50),
    pri_apellido character varying(50) NOT NULL,
    seg_apellido character varying(50),
    id_tipo_documento integer NOT NULL,
    num_documento character varying(10) NOT NULL,
    fec_nacimiento date NOT NULL,
    id_domicilio integer NOT NULL,
    email character varying(50) NOT NULL,
    contrasenia character varying(50) NOT NULL,
    id_tipo_usuario integer NOT NULL,
    id_categoria_socio integer,
    dif_auditiva boolean NOT NULL,
    len_senias boolean NOT NULL,
    id_subcomision integer,
    estado boolean DEFAULT true NOT NULL,
    CONSTRAINT usuarios_email_check CHECK (((email)::text ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'::text))
);


ALTER TABLE proyecto.usuarios OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 19336)
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE; Schema: proyecto; Owner: postgres
--

CREATE SEQUENCE proyecto.usuarios_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE proyecto.usuarios_id_usuario_seq OWNER TO postgres;

--
-- TOC entry 5072 (class 0 OID 0)
-- Dependencies: 226
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: proyecto; Owner: postgres
--

ALTER SEQUENCE proyecto.usuarios_id_usuario_seq OWNED BY proyecto.usuarios.id_usuario;


--
-- TOC entry 4737 (class 2604 OID 19398)
-- Name: actividades id_actividad; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.actividades ALTER COLUMN id_actividad SET DEFAULT nextval('proyecto.actividades_id_actividad_seq'::regclass);


--
-- TOC entry 4756 (class 2604 OID 19517)
-- Name: auditorias id_auditoria; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.auditorias ALTER COLUMN id_auditoria SET DEFAULT nextval('proyecto.auditorias_id_auditoria_seq'::regclass);


--
-- TOC entry 4749 (class 2604 OID 19475)
-- Name: cancelaciones id_cancelacion; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.cancelaciones ALTER COLUMN id_cancelacion SET DEFAULT nextval('proyecto.cancelaciones_id_cancelacion_seq'::regclass);


--
-- TOC entry 4729 (class 2604 OID 19330)
-- Name: cate_socios id_categoria_socio; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.cate_socios ALTER COLUMN id_categoria_socio SET DEFAULT nextval('proyecto.cate_socios_id_categoria_socio_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 19295)
-- Name: departamentos id_departamento; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.departamentos ALTER COLUMN id_departamento SET DEFAULT nextval('proyecto.departamentos_id_departamento_seq'::regclass);


--
-- TOC entry 4727 (class 2604 OID 19317)
-- Name: domicilios id_domicilio; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.domicilios ALTER COLUMN id_domicilio SET DEFAULT nextval('proyecto.domicilios_id_domicilio_seq'::regclass);


--
-- TOC entry 4741 (class 2604 OID 19416)
-- Name: espacios id_espacio; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.espacios ALTER COLUMN id_espacio SET DEFAULT nextval('proyecto.espacios_id_espacio_seq'::regclass);


--
-- TOC entry 4754 (class 2604 OID 19507)
-- Name: funcionalidades id_funcionalidad; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.funcionalidades ALTER COLUMN id_funcionalidad SET DEFAULT nextval('proyecto.funcionalidades_id_funcionalidad_seq'::regclass);


--
-- TOC entry 4747 (class 2604 OID 19457)
-- Name: inscripciones id_inscripcion; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.inscripciones ALTER COLUMN id_inscripcion SET DEFAULT nextval('proyecto.inscripciones_id_inscripcion_seq'::regclass);


--
-- TOC entry 4743 (class 2604 OID 19426)
-- Name: pagos id_pago; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.pagos ALTER COLUMN id_pago SET DEFAULT nextval('proyecto.pagos_id_pago_seq'::regclass);


--
-- TOC entry 4752 (class 2604 OID 19497)
-- Name: perfiles id_perfil; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.perfiles ALTER COLUMN id_perfil SET DEFAULT nextval('proyecto.perfiles_id_perfil_seq'::regclass);


--
-- TOC entry 4745 (class 2604 OID 19439)
-- Name: reservas id_reserva; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.reservas ALTER COLUMN id_reserva SET DEFAULT nextval('proyecto.reservas_id_reserva_seq'::regclass);


--
-- TOC entry 4722 (class 2604 OID 19285)
-- Name: subcomisiones id_subcomision; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.subcomisiones ALTER COLUMN id_subcomision SET DEFAULT nextval('proyecto.subcomisiones_id_subcomision_seq'::regclass);


--
-- TOC entry 4733 (class 2604 OID 19373)
-- Name: telefonos id_telefono; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.telefonos ALTER COLUMN id_telefono SET DEFAULT nextval('proyecto.telefonos_id_telefono_seq'::regclass);


--
-- TOC entry 4735 (class 2604 OID 19388)
-- Name: tipo_actividades id_tipo_actividad; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_actividades ALTER COLUMN id_tipo_actividad SET DEFAULT nextval('proyecto.tipo_actividades_id_tipo_actividad_seq'::regclass);


--
-- TOC entry 4750 (class 2604 OID 19487)
-- Name: tipo_documentos id_tipo_documento; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_documentos ALTER COLUMN id_tipo_documento SET DEFAULT nextval('proyecto.tipo_documentos_id_tipo_documento_seq'::regclass);


--
-- TOC entry 4725 (class 2604 OID 19307)
-- Name: tipo_usuarios id_tipo_usuario; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_usuarios ALTER COLUMN id_tipo_usuario SET DEFAULT nextval('proyecto.tipo_usuarios_id_tipo_usuario_seq'::regclass);


--
-- TOC entry 4731 (class 2604 OID 19340)
-- Name: usuarios id_usuario; Type: DEFAULT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios ALTER COLUMN id_usuario SET DEFAULT nextval('proyecto.usuarios_id_usuario_seq'::regclass);


--
-- TOC entry 4996 (class 0 OID 19395)
-- Dependencies: 233
-- Data for Name: actividades; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.actividades (id_actividad, nombre, descripcion, fecha, id_tipo_actividad, cupo, can_inscriptos, can_cancelados, estado) FROM stdin;
1	Evento Asur	Un Evento al aire libre. 50 personas. Valor ticket entrada $100 socios / $150 no socios.	2024-11-29 00:00:00	4	100	0	0	t
2	Torneo Fútbol 5	Torneo para recaudar fondos para la asociación. 40 personas. Valor ticket entrada $150 socios / $200 no socios.	2025-02-08 15:00:00	1	100	0	0	t
\.


--
-- TOC entry 5014 (class 0 OID 19514)
-- Dependencies: 251
-- Data for Name: auditorias; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.auditorias (id_auditoria, fec_hora, terminal, id_usuario, operacion) FROM stdin;
\.


--
-- TOC entry 5006 (class 0 OID 19472)
-- Dependencies: 243
-- Data for Name: cancelaciones; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.cancelaciones (id_cancelacion, id_inscripcion, fec_cancelacion) FROM stdin;
\.


--
-- TOC entry 4988 (class 0 OID 19327)
-- Dependencies: 225
-- Data for Name: cate_socios; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.cate_socios (id_categoria_socio, nombre, descripcion, estado) FROM stdin;
1	Escolar	Menor de 15 años, no paga cuota.	t
2	Cadete	Joven de 16 a 17 años, paga media cuota.	t
3	Suscriptor	Mayor de 18 años, paga cuota normal.	t
4	Activo	Socio con +1 año de antigüedad, paga cuota normal.	t
5	Colaborador	Oyente, paga cuota normal.	t
6	Jubilado	Jubilado laboralmente, paga media cuota.	t
7	No socio	Usuario no socio, no paga cuota.	t
\.


--
-- TOC entry 4982 (class 0 OID 19292)
-- Dependencies: 219
-- Data for Name: departamentos; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.departamentos (id_departamento, nombre) FROM stdin;
1	Artigas
2	Canelones
3	Cerro Largo
4	Colonia
5	Durazno
6	Flores
7	Florida
8	Lavalleja
9	Maldonado
10	Montevideo
11	Paysandú
12	Rio Negro
13	Rivera
14	Rocha
15	Salto
16	San José
17	Soriano
18	Tacuarembó
19	Treinta y Tres
\.


--
-- TOC entry 4986 (class 0 OID 19314)
-- Dependencies: 223
-- Data for Name: domicilios; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.domicilios (id_domicilio, id_departamento, ciudad, calle, num_puerta, piso, apartamento, estado) FROM stdin;
1	8	Montevideo	Alberto Ricaldi	3456	\N	\N	t
2	3	Rocha	Tomás Langosta	896	\N	\N	t
\.


--
-- TOC entry 4998 (class 0 OID 19413)
-- Dependencies: 235
-- Data for Name: espacios; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.espacios (id_espacio, nombre, ubicacion, capacidad, fec_vigencia_precios, pre_reserva_socios, pre_reserva_no_socios, estado) FROM stdin;
1	Salón A	Primer Piso	100	2026-01-01	150.00	200.00	t
2	Salon B	Planta Baja	150	2027-01-01	200.00	300.00	t
3	Barbacoa	Planta baja: Patio	50	2026-06-01	100.00	150.00	t
4	Cancha de fútbol	Planta baja: patio	200	2026-01-01	150.00	200.00	t
\.


--
-- TOC entry 5012 (class 0 OID 19504)
-- Dependencies: 249
-- Data for Name: funcionalidades; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.funcionalidades (id_funcionalidad, nombre, descripcion, estado) FROM stdin;
\.


--
-- TOC entry 5004 (class 0 OID 19454)
-- Dependencies: 241
-- Data for Name: inscripciones; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.inscripciones (id_inscripcion, id_usuario, id_actividad, fec_inscripcion, estado) FROM stdin;
\.


--
-- TOC entry 5000 (class 0 OID 19423)
-- Dependencies: 237
-- Data for Name: pagos; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.pagos (id_pago, fecha, monto, id_usuario, estado) FROM stdin;
\.


--
-- TOC entry 5010 (class 0 OID 19494)
-- Dependencies: 247
-- Data for Name: perfiles; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.perfiles (id_perfil, nombre, descripcion, estado) FROM stdin;
\.


--
-- TOC entry 5002 (class 0 OID 19436)
-- Dependencies: 239
-- Data for Name: reservas; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.reservas (id_reserva, id_usuario, fec_creada, fec_reserva, hor_inicio, hor_fin, id_espacio, mon_senia, saldo, mon_total, can_personas, estado) FROM stdin;
\.


--
-- TOC entry 4980 (class 0 OID 19282)
-- Dependencies: 217
-- Data for Name: subcomisiones; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.subcomisiones (id_subcomision, nombre, descripcion, estado) FROM stdin;
1	Educación e Inclusión	Promoción de la educación bilingüe y Formación en derechos de personas sordas	t
2	Cultura y Lengua de Señas	Organización de talleres y cursos de Lengua de Señas	t
3	Salud y Bienestar	Promoción de servicios de salud accesibles para personas sordas	t
4	Deportes y Recreación	Organización de eventos deportivos inclusivos	t
5	Finanzas y Administración	Gestión de fondos y presupuestos para actividades de la organización	t
\.


--
-- TOC entry 4992 (class 0 OID 19370)
-- Dependencies: 229
-- Data for Name: telefonos; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.telefonos (id_telefono, id_usuario, num_telefono, estado) FROM stdin;
\.


--
-- TOC entry 4994 (class 0 OID 19385)
-- Dependencies: 231
-- Data for Name: tipo_actividades; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.tipo_actividades (id_tipo_actividad, nombre, descripcion, estado) FROM stdin;
1	Torneo Deportivo	Torneos deportivos lucrativos o recreativos.	t
2	Deporte	Actividades físicas y deportivas, como fútbol, básquetbol, gimnasia y más, diseñadas para promover la salud física.	t
3	Educativa	Reuniones con el fin de enseñar y compartir conocimientos en diversas áreas de interés.	t
4	Social	Eventos sociales como comidas, reuniones y fiestas para fomentar la interacción.	t
5	Taller	Talleres prácticos para aprender y desarrollar diversos temas.	t
6	Lucrativa	Actividad para recaudar fondos como venta de comidas, rifas, subastas y demás.	t
7	Tecnológica	Talleres y seminarios sobre tecnología y nuevas tendencias digitales.	t
8	Cultural	Eventos que celebran y promueven la cultura, como exposiciones de arte, conciertos y festivales.	t
\.


--
-- TOC entry 5008 (class 0 OID 19484)
-- Dependencies: 245
-- Data for Name: tipo_documentos; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.tipo_documentos (id_tipo_documento, nombre, estado) FROM stdin;
1	Cédula de Identidad	t
2	Pasaporte	t
\.


--
-- TOC entry 4984 (class 0 OID 19304)
-- Dependencies: 221
-- Data for Name: tipo_usuarios; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.tipo_usuarios (id_tipo_usuario, nombre, descripcion, estado) FROM stdin;
1	Administrador	Usuario administrador del sistema	t
2	Auxiliar administrativo	Usuario con privilegios elevados	t
3	Socio	Usuarios socios de ASUR	t
4	No socio	Usuarios no Socios de ASUR	t
\.


--
-- TOC entry 4990 (class 0 OID 19337)
-- Dependencies: 227
-- Data for Name: usuarios; Type: TABLE DATA; Schema: proyecto; Owner: postgres
--

COPY proyecto.usuarios (id_usuario, pri_nombre, seg_nombre, pri_apellido, seg_apellido, id_tipo_documento, num_documento, fec_nacimiento, id_domicilio, email, contrasenia, id_tipo_usuario, id_categoria_socio, dif_auditiva, len_senias, id_subcomision, estado) FROM stdin;
\.


--
-- TOC entry 5074 (class 0 OID 0)
-- Dependencies: 232
-- Name: actividades_id_actividad_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.actividades_id_actividad_seq', 2, true);


--
-- TOC entry 5075 (class 0 OID 0)
-- Dependencies: 250
-- Name: auditorias_id_auditoria_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.auditorias_id_auditoria_seq', 1, false);


--
-- TOC entry 5076 (class 0 OID 0)
-- Dependencies: 242
-- Name: cancelaciones_id_cancelacion_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.cancelaciones_id_cancelacion_seq', 1, false);


--
-- TOC entry 5077 (class 0 OID 0)
-- Dependencies: 224
-- Name: cate_socios_id_categoria_socio_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.cate_socios_id_categoria_socio_seq', 7, true);


--
-- TOC entry 5078 (class 0 OID 0)
-- Dependencies: 218
-- Name: departamentos_id_departamento_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.departamentos_id_departamento_seq', 19, true);


--
-- TOC entry 5079 (class 0 OID 0)
-- Dependencies: 222
-- Name: domicilios_id_domicilio_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.domicilios_id_domicilio_seq', 2, true);


--
-- TOC entry 5080 (class 0 OID 0)
-- Dependencies: 234
-- Name: espacios_id_espacio_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.espacios_id_espacio_seq', 4, true);


--
-- TOC entry 5081 (class 0 OID 0)
-- Dependencies: 248
-- Name: funcionalidades_id_funcionalidad_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.funcionalidades_id_funcionalidad_seq', 1, false);


--
-- TOC entry 5082 (class 0 OID 0)
-- Dependencies: 240
-- Name: inscripciones_id_inscripcion_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.inscripciones_id_inscripcion_seq', 1, false);


--
-- TOC entry 5083 (class 0 OID 0)
-- Dependencies: 236
-- Name: pagos_id_pago_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.pagos_id_pago_seq', 1, false);


--
-- TOC entry 5084 (class 0 OID 0)
-- Dependencies: 246
-- Name: perfiles_id_perfil_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.perfiles_id_perfil_seq', 1, false);


--
-- TOC entry 5085 (class 0 OID 0)
-- Dependencies: 238
-- Name: reservas_id_reserva_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.reservas_id_reserva_seq', 1, false);


--
-- TOC entry 5086 (class 0 OID 0)
-- Dependencies: 216
-- Name: subcomisiones_id_subcomision_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.subcomisiones_id_subcomision_seq', 5, true);


--
-- TOC entry 5087 (class 0 OID 0)
-- Dependencies: 228
-- Name: telefonos_id_telefono_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.telefonos_id_telefono_seq', 1, false);


--
-- TOC entry 5088 (class 0 OID 0)
-- Dependencies: 230
-- Name: tipo_actividades_id_tipo_actividad_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.tipo_actividades_id_tipo_actividad_seq', 8, true);


--
-- TOC entry 5089 (class 0 OID 0)
-- Dependencies: 244
-- Name: tipo_documentos_id_tipo_documento_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.tipo_documentos_id_tipo_documento_seq', 2, true);


--
-- TOC entry 5090 (class 0 OID 0)
-- Dependencies: 220
-- Name: tipo_usuarios_id_tipo_usuario_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.tipo_usuarios_id_tipo_usuario_seq', 4, true);


--
-- TOC entry 5091 (class 0 OID 0)
-- Dependencies: 226
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE SET; Schema: proyecto; Owner: postgres
--

SELECT pg_catalog.setval('proyecto.usuarios_id_usuario_seq', 1, false);


--
-- TOC entry 4795 (class 2606 OID 19406)
-- Name: actividades actividades_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.actividades
    ADD CONSTRAINT actividades_pkey PRIMARY KEY (id_actividad);


--
-- TOC entry 4821 (class 2606 OID 19519)
-- Name: auditorias auditorias_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.auditorias
    ADD CONSTRAINT auditorias_pkey PRIMARY KEY (id_auditoria);


--
-- TOC entry 4807 (class 2606 OID 19477)
-- Name: cancelaciones cancelaciones_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.cancelaciones
    ADD CONSTRAINT cancelaciones_pkey PRIMARY KEY (id_cancelacion);


--
-- TOC entry 4777 (class 2606 OID 19335)
-- Name: cate_socios cate_socios_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.cate_socios
    ADD CONSTRAINT cate_socios_nombre_key UNIQUE (nombre);


--
-- TOC entry 4779 (class 2606 OID 19333)
-- Name: cate_socios cate_socios_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.cate_socios
    ADD CONSTRAINT cate_socios_pkey PRIMARY KEY (id_categoria_socio);


--
-- TOC entry 4767 (class 2606 OID 19300)
-- Name: departamentos departamentos_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.departamentos
    ADD CONSTRAINT departamentos_nombre_key UNIQUE (nombre);


--
-- TOC entry 4769 (class 2606 OID 19298)
-- Name: departamentos departamentos_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.departamentos
    ADD CONSTRAINT departamentos_pkey PRIMARY KEY (id_departamento);


--
-- TOC entry 4775 (class 2606 OID 19320)
-- Name: domicilios domicilios_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.domicilios
    ADD CONSTRAINT domicilios_pkey PRIMARY KEY (id_domicilio);


--
-- TOC entry 4797 (class 2606 OID 19421)
-- Name: espacios espacios_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.espacios
    ADD CONSTRAINT espacios_nombre_key UNIQUE (nombre);


--
-- TOC entry 4799 (class 2606 OID 19419)
-- Name: espacios espacios_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.espacios
    ADD CONSTRAINT espacios_pkey PRIMARY KEY (id_espacio);


--
-- TOC entry 4817 (class 2606 OID 19512)
-- Name: funcionalidades funcionalidades_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.funcionalidades
    ADD CONSTRAINT funcionalidades_nombre_key UNIQUE (nombre);


--
-- TOC entry 4819 (class 2606 OID 19510)
-- Name: funcionalidades funcionalidades_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.funcionalidades
    ADD CONSTRAINT funcionalidades_pkey PRIMARY KEY (id_funcionalidad);


--
-- TOC entry 4805 (class 2606 OID 19460)
-- Name: inscripciones inscripciones_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.inscripciones
    ADD CONSTRAINT inscripciones_pkey PRIMARY KEY (id_inscripcion);


--
-- TOC entry 4801 (class 2606 OID 19429)
-- Name: pagos pagos_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.pagos
    ADD CONSTRAINT pagos_pkey PRIMARY KEY (id_pago);


--
-- TOC entry 4813 (class 2606 OID 19502)
-- Name: perfiles perfiles_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.perfiles
    ADD CONSTRAINT perfiles_nombre_key UNIQUE (nombre);


--
-- TOC entry 4815 (class 2606 OID 19500)
-- Name: perfiles perfiles_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.perfiles
    ADD CONSTRAINT perfiles_pkey PRIMARY KEY (id_perfil);


--
-- TOC entry 4803 (class 2606 OID 19442)
-- Name: reservas reservas_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.reservas
    ADD CONSTRAINT reservas_pkey PRIMARY KEY (id_reserva);


--
-- TOC entry 4763 (class 2606 OID 19290)
-- Name: subcomisiones subcomisiones_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.subcomisiones
    ADD CONSTRAINT subcomisiones_nombre_key UNIQUE (nombre);


--
-- TOC entry 4765 (class 2606 OID 19288)
-- Name: subcomisiones subcomisiones_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.subcomisiones
    ADD CONSTRAINT subcomisiones_pkey PRIMARY KEY (id_subcomision);


--
-- TOC entry 4787 (class 2606 OID 19378)
-- Name: telefonos telefonos_num_telefono_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.telefonos
    ADD CONSTRAINT telefonos_num_telefono_key UNIQUE (num_telefono);


--
-- TOC entry 4789 (class 2606 OID 19376)
-- Name: telefonos telefonos_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.telefonos
    ADD CONSTRAINT telefonos_pkey PRIMARY KEY (id_telefono);


--
-- TOC entry 4791 (class 2606 OID 19393)
-- Name: tipo_actividades tipo_actividades_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_actividades
    ADD CONSTRAINT tipo_actividades_nombre_key UNIQUE (nombre);


--
-- TOC entry 4793 (class 2606 OID 19391)
-- Name: tipo_actividades tipo_actividades_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_actividades
    ADD CONSTRAINT tipo_actividades_pkey PRIMARY KEY (id_tipo_actividad);


--
-- TOC entry 4809 (class 2606 OID 19492)
-- Name: tipo_documentos tipo_documentos_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_documentos
    ADD CONSTRAINT tipo_documentos_nombre_key UNIQUE (nombre);


--
-- TOC entry 4811 (class 2606 OID 19490)
-- Name: tipo_documentos tipo_documentos_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_documentos
    ADD CONSTRAINT tipo_documentos_pkey PRIMARY KEY (id_tipo_documento);


--
-- TOC entry 4771 (class 2606 OID 19312)
-- Name: tipo_usuarios tipo_usuarios_nombre_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_usuarios
    ADD CONSTRAINT tipo_usuarios_nombre_key UNIQUE (nombre);


--
-- TOC entry 4773 (class 2606 OID 19310)
-- Name: tipo_usuarios tipo_usuarios_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.tipo_usuarios
    ADD CONSTRAINT tipo_usuarios_pkey PRIMARY KEY (id_tipo_usuario);


--
-- TOC entry 4781 (class 2606 OID 19348)
-- Name: usuarios usuarios_email_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_email_key UNIQUE (email);


--
-- TOC entry 4783 (class 2606 OID 19346)
-- Name: usuarios usuarios_num_documento_key; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_num_documento_key UNIQUE (num_documento);


--
-- TOC entry 4785 (class 2606 OID 19344)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id_usuario);


--
-- TOC entry 4828 (class 2606 OID 19407)
-- Name: actividades actividades_id_tipo_actividad_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.actividades
    ADD CONSTRAINT actividades_id_tipo_actividad_fkey FOREIGN KEY (id_tipo_actividad) REFERENCES proyecto.tipo_actividades(id_tipo_actividad) ON DELETE RESTRICT;


--
-- TOC entry 4834 (class 2606 OID 19478)
-- Name: cancelaciones cancelaciones_id_inscripcion_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.cancelaciones
    ADD CONSTRAINT cancelaciones_id_inscripcion_fkey FOREIGN KEY (id_inscripcion) REFERENCES proyecto.inscripciones(id_inscripcion) ON DELETE RESTRICT;


--
-- TOC entry 4822 (class 2606 OID 19321)
-- Name: domicilios domicilios_id_departamento_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.domicilios
    ADD CONSTRAINT domicilios_id_departamento_fkey FOREIGN KEY (id_departamento) REFERENCES proyecto.departamentos(id_departamento) ON DELETE RESTRICT;


--
-- TOC entry 4835 (class 2606 OID 19520)
-- Name: auditorias fk_auditoria_usuario; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.auditorias
    ADD CONSTRAINT fk_auditoria_usuario FOREIGN KEY (id_usuario) REFERENCES proyecto.usuarios(id_usuario);


--
-- TOC entry 4827 (class 2606 OID 19379)
-- Name: telefonos fk_telefono_usuario; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.telefonos
    ADD CONSTRAINT fk_telefono_usuario FOREIGN KEY (id_usuario) REFERENCES proyecto.usuarios(id_usuario);


--
-- TOC entry 4832 (class 2606 OID 19466)
-- Name: inscripciones inscripciones_id_actividad_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.inscripciones
    ADD CONSTRAINT inscripciones_id_actividad_fkey FOREIGN KEY (id_actividad) REFERENCES proyecto.actividades(id_actividad) ON DELETE RESTRICT;


--
-- TOC entry 4833 (class 2606 OID 19461)
-- Name: inscripciones inscripciones_id_usuario_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.inscripciones
    ADD CONSTRAINT inscripciones_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES proyecto.usuarios(id_usuario) ON DELETE RESTRICT;


--
-- TOC entry 4829 (class 2606 OID 19430)
-- Name: pagos pagos_id_usuario_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.pagos
    ADD CONSTRAINT pagos_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES proyecto.usuarios(id_usuario) ON DELETE RESTRICT;


--
-- TOC entry 4830 (class 2606 OID 19448)
-- Name: reservas reservas_id_espacio_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.reservas
    ADD CONSTRAINT reservas_id_espacio_fkey FOREIGN KEY (id_espacio) REFERENCES proyecto.espacios(id_espacio) ON DELETE RESTRICT;


--
-- TOC entry 4831 (class 2606 OID 19443)
-- Name: reservas reservas_id_usuario_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.reservas
    ADD CONSTRAINT reservas_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES proyecto.usuarios(id_usuario) ON DELETE RESTRICT;


--
-- TOC entry 4823 (class 2606 OID 19359)
-- Name: usuarios usuarios_id_categoria_socio_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_id_categoria_socio_fkey FOREIGN KEY (id_categoria_socio) REFERENCES proyecto.cate_socios(id_categoria_socio) ON DELETE RESTRICT;


--
-- TOC entry 4824 (class 2606 OID 19349)
-- Name: usuarios usuarios_id_domicilio_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_id_domicilio_fkey FOREIGN KEY (id_domicilio) REFERENCES proyecto.domicilios(id_domicilio) ON DELETE RESTRICT;


--
-- TOC entry 4825 (class 2606 OID 19364)
-- Name: usuarios usuarios_id_subcomision_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_id_subcomision_fkey FOREIGN KEY (id_subcomision) REFERENCES proyecto.subcomisiones(id_subcomision) ON DELETE RESTRICT;


--
-- TOC entry 4826 (class 2606 OID 19354)
-- Name: usuarios usuarios_id_tipo_usuario_fkey; Type: FK CONSTRAINT; Schema: proyecto; Owner: postgres
--

ALTER TABLE ONLY proyecto.usuarios
    ADD CONSTRAINT usuarios_id_tipo_usuario_fkey FOREIGN KEY (id_tipo_usuario) REFERENCES proyecto.tipo_usuarios(id_tipo_usuario) ON DELETE RESTRICT;


--
-- TOC entry 5020 (class 0 OID 0)
-- Dependencies: 233
-- Name: TABLE actividades; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.actividades TO proyecto;


--
-- TOC entry 5022 (class 0 OID 0)
-- Dependencies: 232
-- Name: SEQUENCE actividades_id_actividad_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.actividades_id_actividad_seq TO proyecto;


--
-- TOC entry 5023 (class 0 OID 0)
-- Dependencies: 251
-- Name: TABLE auditorias; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.auditorias TO proyecto;


--
-- TOC entry 5025 (class 0 OID 0)
-- Dependencies: 250
-- Name: SEQUENCE auditorias_id_auditoria_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.auditorias_id_auditoria_seq TO proyecto;


--
-- TOC entry 5026 (class 0 OID 0)
-- Dependencies: 243
-- Name: TABLE cancelaciones; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.cancelaciones TO proyecto;


--
-- TOC entry 5028 (class 0 OID 0)
-- Dependencies: 242
-- Name: SEQUENCE cancelaciones_id_cancelacion_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.cancelaciones_id_cancelacion_seq TO proyecto;


--
-- TOC entry 5029 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE cate_socios; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.cate_socios TO proyecto;


--
-- TOC entry 5031 (class 0 OID 0)
-- Dependencies: 224
-- Name: SEQUENCE cate_socios_id_categoria_socio_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.cate_socios_id_categoria_socio_seq TO proyecto;


--
-- TOC entry 5032 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE departamentos; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.departamentos TO proyecto;


--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 218
-- Name: SEQUENCE departamentos_id_departamento_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.departamentos_id_departamento_seq TO proyecto;


--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE domicilios; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.domicilios TO proyecto;


--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 222
-- Name: SEQUENCE domicilios_id_domicilio_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.domicilios_id_domicilio_seq TO proyecto;


--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE espacios; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.espacios TO proyecto;


--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 234
-- Name: SEQUENCE espacios_id_espacio_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.espacios_id_espacio_seq TO proyecto;


--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 249
-- Name: TABLE funcionalidades; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.funcionalidades TO proyecto;


--
-- TOC entry 5043 (class 0 OID 0)
-- Dependencies: 248
-- Name: SEQUENCE funcionalidades_id_funcionalidad_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.funcionalidades_id_funcionalidad_seq TO proyecto;


--
-- TOC entry 5044 (class 0 OID 0)
-- Dependencies: 241
-- Name: TABLE inscripciones; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.inscripciones TO proyecto;


--
-- TOC entry 5046 (class 0 OID 0)
-- Dependencies: 240
-- Name: SEQUENCE inscripciones_id_inscripcion_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.inscripciones_id_inscripcion_seq TO proyecto;


--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 237
-- Name: TABLE pagos; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.pagos TO proyecto;


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 236
-- Name: SEQUENCE pagos_id_pago_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.pagos_id_pago_seq TO proyecto;


--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 247
-- Name: TABLE perfiles; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.perfiles TO proyecto;


--
-- TOC entry 5052 (class 0 OID 0)
-- Dependencies: 246
-- Name: SEQUENCE perfiles_id_perfil_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.perfiles_id_perfil_seq TO proyecto;


--
-- TOC entry 5053 (class 0 OID 0)
-- Dependencies: 239
-- Name: TABLE reservas; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.reservas TO proyecto;


--
-- TOC entry 5055 (class 0 OID 0)
-- Dependencies: 238
-- Name: SEQUENCE reservas_id_reserva_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.reservas_id_reserva_seq TO proyecto;


--
-- TOC entry 5056 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE subcomisiones; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.subcomisiones TO proyecto;


--
-- TOC entry 5058 (class 0 OID 0)
-- Dependencies: 216
-- Name: SEQUENCE subcomisiones_id_subcomision_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.subcomisiones_id_subcomision_seq TO proyecto;


--
-- TOC entry 5059 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE telefonos; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.telefonos TO proyecto;


--
-- TOC entry 5061 (class 0 OID 0)
-- Dependencies: 228
-- Name: SEQUENCE telefonos_id_telefono_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.telefonos_id_telefono_seq TO proyecto;


--
-- TOC entry 5062 (class 0 OID 0)
-- Dependencies: 231
-- Name: TABLE tipo_actividades; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.tipo_actividades TO proyecto;


--
-- TOC entry 5064 (class 0 OID 0)
-- Dependencies: 230
-- Name: SEQUENCE tipo_actividades_id_tipo_actividad_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.tipo_actividades_id_tipo_actividad_seq TO proyecto;


--
-- TOC entry 5065 (class 0 OID 0)
-- Dependencies: 245
-- Name: TABLE tipo_documentos; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.tipo_documentos TO proyecto;


--
-- TOC entry 5067 (class 0 OID 0)
-- Dependencies: 244
-- Name: SEQUENCE tipo_documentos_id_tipo_documento_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.tipo_documentos_id_tipo_documento_seq TO proyecto;


--
-- TOC entry 5068 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE tipo_usuarios; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.tipo_usuarios TO proyecto;


--
-- TOC entry 5070 (class 0 OID 0)
-- Dependencies: 220
-- Name: SEQUENCE tipo_usuarios_id_tipo_usuario_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.tipo_usuarios_id_tipo_usuario_seq TO proyecto;


--
-- TOC entry 5071 (class 0 OID 0)
-- Dependencies: 227
-- Name: TABLE usuarios; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON TABLE proyecto.usuarios TO proyecto;


--
-- TOC entry 5073 (class 0 OID 0)
-- Dependencies: 226
-- Name: SEQUENCE usuarios_id_usuario_seq; Type: ACL; Schema: proyecto; Owner: postgres
--

GRANT ALL ON SEQUENCE proyecto.usuarios_id_usuario_seq TO proyecto;


--
-- TOC entry 2125 (class 826 OID 19280)
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: proyecto; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA proyecto GRANT ALL ON SEQUENCES TO proyecto;


--
-- TOC entry 2124 (class 826 OID 19279)
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: proyecto; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA proyecto GRANT ALL ON TABLES TO proyecto;


-- Completed on 2025-04-23 13:43:30

--
-- PostgreSQL database dump complete
--

