package com.mocktpo.util;

/**
 * Lingoes LD2 File Reader
 *
 * <pre>
 * Lingoes Format overview:
 *
 * General Information:
 * - Dictionary data are stored in deflate streams.
 * - Index group information is stored in an index array in the LD2 file itself.
 * - Numbers are using little endian byte order.
 * - Definitions and xml data have UTF-8 or UTF-16LE encodings.
 *
 * LD2 file schema:
 * - File Header
 * - File Description
 * - Additional Information (optional)
 * - Index Group (corresponds to definitions in dictionary)
 * - Deflated Dictionary Streams
 * -- Index Data
 * --- Offsets of definitions
 * --- Offsets of translations
 * --- Flags
 * --- References to other translations
 * -- Definitions
 * -- Translations (xml)
 *
 * </pre>
 */
public class LingoesLd2Reader {

}
