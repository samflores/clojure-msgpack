(ns msgpack.io
  (:import java.io.ByteArrayOutputStream
           java.io.DataOutputStream
           java.io.ByteArrayInputStream
           java.io.DataInputStream))

(defn ubyte
  "Treat n as if it were an unsigned byte literal. If n is greater
  than the maximum value of a signed byte (127), convert it to a
  negative byte value with the same pattern of bits."
  [n]
  (byte (if (> n 0x7f) (- n 0x100) n)))

(defn ubytes [coll] (byte-array (map ubyte coll)))

(defn- ->bytes
  "Convert a Java primitive to its byte representation."
  [write v]
  (let [output-stream (ByteArrayOutputStream.)
        data-output (DataOutputStream. output-stream)]
    (write data-output v)
    (.toByteArray output-stream)))

(defn byte->bytes [n] (->bytes #(.writeByte %1 %2) n))
(defn short->bytes [n] (->bytes #(.writeShort %1 %2) n))
(defn int->bytes [n] (->bytes #(.writeInt %1 %2) n))
(defn long->bytes [n] (->bytes #(.writeLong %1 %2) n))
(defn float->bytes [x] (->bytes #(.writeFloat %1 %2) x))
(defn double->bytes [x] (->bytes #(.writeDouble %1 %2) x))

(defn byte-stream [bytes]
  (let [input-stream (ByteArrayInputStream. bytes)
        data-input (DataInputStream. input-stream)]
    data-input))

;; DataInputStream
(defn next-byte [stream] (.readByte stream))
(defn next-short [stream] (.readShort stream))
(defn next-int [stream] (.readInt stream))
(defn next-long [stream] (.readLong stream))
(defn next-float [stream] (.readFloat stream))
(defn next-double [stream] (.readDouble stream))