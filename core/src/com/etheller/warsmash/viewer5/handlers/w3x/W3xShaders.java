package com.etheller.warsmash.viewer5.handlers.w3x;

public class W3xShaders {
	public static final class Cliffs {
		private Cliffs() {
		}

		public static final String vert = "\r\n" + //
				"uniform mat4 u_VP;\r\n" + //
				"uniform sampler2D u_heightMap;\r\n" + //
				"uniform vec2 u_pixel;\r\n" + //
				"uniform vec2 u_centerOffset;\r\n" + //
				"attribute vec3 a_position;\r\n" + //
//				"attribute vec3 a_normal;\r\n" + //
				"attribute vec2 a_uv;\r\n" + //
				"attribute vec3 a_instancePosition;\r\n" + //
				"attribute float a_instanceTexture;\r\n" + //
				"varying vec3 v_normal;\r\n" + //
				"varying vec2 v_uv;\r\n" + //
				"varying float v_texture;\r\n" + //
				"varying vec3 v_position;\r\n" + //
				"void main() {\r\n" + //
				"  // Half of a pixel in the cliff height map.\r\n" + //
				"  vec2 halfPixel = u_pixel * 0.5;\r\n" + //
				"  // The bottom left corner of the map tile this vertex is on.\r\n" + //
				"  vec2 corner = floor((a_instancePosition.xy - vec2(1.0, 0.0) - u_centerOffset.xy) / 128.0);\r\n" + //
				"  // Get the 4 closest heights in the height map.\r\n" + //
				"  float bottomLeft = texture2D(u_heightMap, corner * u_pixel + halfPixel).r;\r\n" + //
				"  float bottomRight = texture2D(u_heightMap, (corner + vec2(1.0, 0.0)) * u_pixel + halfPixel).r;\r\n" + //
				"  float topLeft = texture2D(u_heightMap, (corner + vec2(0.0, 1.0)) * u_pixel + halfPixel).r;\r\n" + //
				"  float topRight = texture2D(u_heightMap, (corner + vec2(1.0, 1.0)) * u_pixel + halfPixel).r;\r\n" + //
				"  \r\n" + //
				"  // Do a bilinear interpolation between the heights to get the final value.\r\n" + //
				"  float bottom = mix(bottomRight, bottomLeft, -a_position.x / 128.0);\r\n" + //
				"  float top = mix(topRight, topLeft, -a_position.x / 128.0);\r\n" + //
				"  float height = mix(bottom, top, a_position.y / 128.0);\r\n" + //
//				"  v_normal = a_normal;\r\n" + //
				"  v_uv = a_uv;\r\n" + //
				"  v_texture = a_instanceTexture;\r\n" + //
				"  v_position = a_position + vec3(a_instancePosition.xy, a_instancePosition.z + height * 128.0);\r\n" + //
				"  gl_Position = u_VP * vec4(v_position, 1.0);\r\n" + //
				"}\r\n" + //
				"";

		public static final String frag = "\r\n" + //
				"// #extension GL_OES_standard_derivatives : enable\r\n" + //
				"precision mediump float;\r\n" + //
				"uniform sampler2D u_texture1;\r\n" + //
				"uniform sampler2D u_texture2;\r\n" + //
//				"varying vec3 v_normal;\r\n" + //
				"varying vec2 v_uv;\r\n" + //
				"varying float v_texture;\r\n" + //
				"varying vec3 v_position;\r\n" + //
				"// const vec3 lightDirection = normalize(vec3(-0.3, -0.3, 0.25));\r\n" + //
				"vec4 sample(int texture, vec2 uv) {\r\n" + //
				"  if (texture == 0) {\r\n" + //
				"    return texture2D(u_texture1, uv);\r\n" + //
				"  } else {\r\n" + //
				"    return texture2D(u_texture2, uv);\r\n" + //
				"  }\r\n" + //
				"}\r\n" + //
				"void main() {\r\n" + //
				"  vec4 color = sample(int(v_texture), v_uv);\r\n" + //
				"  // vec3 faceNormal = cross(dFdx(v_position), dFdy(v_position));\r\n" + //
				"  // vec3 normal = normalize((faceNormal + v_normal) * 0.5);\r\n" + //
				"  // color *= clamp(dot(normal, lightDirection) + 0.45, 0.1, 1.0);\r\n" + //
				"  gl_FragColor = color;\r\n" + //
				"}\r\n" + //
				"";
	}

	public static final class Ground {
		private Ground() {
		}

		public static final String frag = "\r\n" + //
				"precision mediump float;\r\n" + //
				"uniform sampler2D u_tilesets[15];\r\n" + //
				"varying vec4 v_tilesets;\r\n" + //
				"varying vec2 v_uv[4];\r\n" + //
				"varying vec3 v_normal;\r\n" + //
				"const vec3 lightDirection = normalize(vec3(-0.3, -0.3, 0.25));\r\n" + //
				"vec4 sample(float tileset, vec2 uv) {\r\n" + //
				"  if (tileset <= 0.5) {\r\n" + //
				"    return texture2D(u_tilesets[0], uv);\r\n" + //
				"  } else if (tileset <= 1.5) {\r\n" + //
				"    return texture2D(u_tilesets[1], uv);\r\n" + //
				"  } else if (tileset <= 2.5) {\r\n" + //
				"    return texture2D(u_tilesets[2], uv);\r\n" + //
				"  } else if (tileset <= 3.5) {\r\n" + //
				"    return texture2D(u_tilesets[3], uv);\r\n" + //
				"  } else if (tileset <= 4.5) {\r\n" + //
				"    return texture2D(u_tilesets[4], uv);\r\n" + //
				"  } else if (tileset <= 5.5) {\r\n" + //
				"    return texture2D(u_tilesets[5], uv);\r\n" + //
				"  } else if (tileset <= 6.5) {\r\n" + //
				"    return texture2D(u_tilesets[6], uv);\r\n" + //
				"  } else if (tileset <= 7.5) {\r\n" + //
				"    return texture2D(u_tilesets[7], uv);\r\n" + //
				"  } else if (tileset <= 8.5) {\r\n" + //
				"    return texture2D(u_tilesets[8], uv);\r\n" + //
				"  } else if (tileset <= 9.5) {\r\n" + //
				"    return texture2D(u_tilesets[9], uv);\r\n" + //
				"  } else if (tileset <= 10.5) {\r\n" + //
				"    return texture2D(u_tilesets[10], uv);\r\n" + //
				"  } else if (tileset <= 11.5) {\r\n" + //
				"    return texture2D(u_tilesets[11], uv);\r\n" + //
				"  } else if (tileset <= 12.5) {\r\n" + //
				"    return texture2D(u_tilesets[12], uv);\r\n" + //
				"  } else if (tileset <= 13.5) {\r\n" + //
				"    return texture2D(u_tilesets[13], uv);\r\n" + //
				"  } else if (tileset <= 14.5) {\r\n" + //
				"    return texture2D(u_tilesets[14], uv);\r\n" + //
				"  }\r\n" + //
				"}\r\n" + //
				"vec4 blend(vec4 color, float tileset, vec2 uv) {\r\n" + //
				"  vec4 texel = sample(tileset, uv);\r\n" + //
				"  return mix(color, texel, texel.a);\r\n" + //
				"}\r\n" + //
				"void main() {\r\n" + //
				"  vec4 color = sample(v_tilesets[0] - 1.0, v_uv[0]);\r\n" + //
				"  if (v_tilesets[1] > 0.5) {\r\n" + //
				"    color = blend(color, v_tilesets[1] - 1.0, v_uv[1]);\r\n" + //
				"  }\r\n" + //
				"  if (v_tilesets[2] > 0.5) {\r\n" + //
				"    color = blend(color, v_tilesets[2] - 1.0, v_uv[2]);\r\n" + //
				"  }\r\n" + //
				"  if (v_tilesets[3] > 0.5) {\r\n" + //
				"    color = blend(color, v_tilesets[3] - 1.0, v_uv[3]);\r\n" + //
				"  }\r\n" + //
				"  // color *= clamp(dot(v_normal, lightDirection) + 0.45, 0.0, 1.0);\r\n" + //
				"  gl_FragColor = color;\r\n" + //
				"}";

		public static final String vert = "\r\n" + //
				"uniform mat4 u_VP;\r\n" + //
				"uniform sampler2D u_heightMap;\r\n" + //
				"uniform vec2 u_size;\r\n" + //
				"uniform vec2 u_offset;\r\n" + //
				"uniform bool u_extended[14];\r\n" + //
				"uniform float u_baseTileset;\r\n" + //
				"attribute vec2 a_position;\r\n" + //
				"attribute float a_InstanceID;\r\n" + //
				"attribute vec4 a_textures;\r\n" + //
				"attribute vec4 a_variations;\r\n" + //
				"varying vec4 v_tilesets;\r\n" + //
				"varying vec2 v_uv[4];\r\n" + //
				"varying vec3 v_normal;\r\n" + //
				"vec2 getCell(float variation) {\r\n" + //
				"  if (variation < 16.0) {\r\n" + //
				"    return vec2(mod(variation, 4.0), floor(variation / 4.0));\r\n" + //
				"  } else {\r\n" + //
				"    variation -= 16.0;\r\n" + //
				"    return vec2(4.0 + mod(variation, 4.0), floor(variation / 4.0));\r\n" + //
				"  }\r\n" + //
				"}\r\n" + //
				"vec2 getUV(vec2 position, bool extended, float variation) {\r\n" + //
				"  vec2 cell = getCell(variation);\r\n" + //
				"  vec2 cellSize = vec2(extended ? 0.125 : 0.25, 0.25);\r\n" + //
				"  vec2 uv = vec2(position.x, 1.0 - position.y);\r\n" + //
				"  vec2 pixelSize = vec2(1.0 / 512.0, 1.0 / 256.0); /// Note: hardcoded to 512x256 for now.\r\n" + //
				"  return clamp((cell + uv) * cellSize, cell * cellSize + pixelSize, (cell + 1.0) * cellSize - pixelSize); \r\n"
				+ //
				"}\r\n" + //
				"void main() {\r\n" + //
				"  vec4 textures = a_textures - u_baseTileset;\r\n" + //
				"  \r\n" + //
				"  if (textures[0] > 0.0 || textures[1] > 0.0 || textures[2] > 0.0 || textures[3] > 0.0) {\r\n" + //
				"    v_tilesets = textures;\r\n" + //
				"    v_uv[0] = getUV(a_position, u_extended[int(textures[0]) - 1], a_variations[0]);\r\n" + //
				"    v_uv[1] = getUV(a_position, u_extended[int(textures[1]) - 1], a_variations[1]);\r\n" + //
				"    v_uv[2] = getUV(a_position, u_extended[int(textures[2]) - 1], a_variations[2]);\r\n" + //
				"    v_uv[3] = getUV(a_position, u_extended[int(textures[3]) - 1], a_variations[3]);\r\n" + //
				"    vec2 corner = vec2(mod(a_InstanceID, u_size.x), floor(a_InstanceID / u_size.x));\r\n" + //
				"    vec2 base = corner + a_position;\r\n" + //
				"    float height = texture2D(u_heightMap, base / u_size).r;\r\n" + //
				"    float hL = texture2D(u_heightMap, vec2(base - vec2(1.0, 0.0)) / (u_size)).r;\r\n" + //
				"    float hR = texture2D(u_heightMap, vec2(base + vec2(1.0, 0.0)) / (u_size)).r;\r\n" + //
				"    float hD = texture2D(u_heightMap, vec2(base - vec2(0.0, 1.0)) / (u_size)).r;\r\n" + //
				"    float hU = texture2D(u_heightMap, vec2(base + vec2(0.0, 1.0)) / (u_size)).r;\r\n" + //
				"    v_normal = normalize(vec3(hL - hR, hD - hU, 2.0));\r\n" + //
				"    gl_Position = u_VP * vec4(base * 128.0 + u_offset, height * 128.0, 1.0);\r\n" + //
				"  } else {\r\n" + //
				"    v_tilesets = vec4(0.0);\r\n" + //
				"    v_uv[0] = vec2(0.0);\r\n" + //
				"    v_uv[1] = vec2(0.0);\r\n" + //
				"    v_uv[2] = vec2(0.0);\r\n" + //
				"    v_uv[3] = vec2(0.0);\r\n" + //
				"    v_normal = vec3(0.0);\r\n" + //
				"    gl_Position = vec4(0.0);\r\n" + //
				"  }\r\n" + //
				"}";
	}

	public static final class Water {
		private Water() {
		}

		public static final String frag = "\r\n" + //
				"precision mediump float;\r\n" + //
				"uniform sampler2D u_waterTexture;\r\n" + //
				"varying vec2 v_uv;\r\n" + //
				"varying vec4 v_color;\r\n" + //
				"void main() {\r\n" + //
				"  gl_FragColor = texture2D(u_waterTexture, v_uv) * v_color;\r\n" + //
				"}\r\n" + //
				"";
		public static final String vert = "\r\n" + //
				"uniform mat4 u_VP;\r\n" + //
				"uniform sampler2D u_heightMap;\r\n" + //
				"uniform sampler2D u_waterHeightMap;\r\n" + //
				"uniform vec2 u_size;\r\n" + //
				"uniform vec2 u_offset;\r\n" + //
				"uniform float u_offsetHeight;\r\n" + //
				"uniform vec4 u_minDeepColor;\r\n" + //
				"uniform vec4 u_maxDeepColor;\r\n" + //
				"uniform vec4 u_minShallowColor;\r\n" + //
				"uniform vec4 u_maxShallowColor;\r\n" + //
				"attribute vec2 a_position;\r\n" + //
				"attribute float a_InstanceID;\r\n" + //
				"attribute float a_isWater;\r\n" + //
				"varying vec2 v_uv;\r\n" + //
				"varying vec4 v_color;\r\n" + //
				"const float minDepth = 10.0 / 128.0;\r\n" + //
				"const float deepLevel = 64.0 / 128.0;\r\n" + //
				"const float maxDepth = 72.0 / 128.0;\r\n" + //
				"void main() {\r\n" + //
				"  if (a_isWater > 0.5) {\r\n" + //
				"    v_uv = a_position;\r\n" + //
				"    vec2 corner = vec2(mod(a_InstanceID, u_size.x), floor(a_InstanceID / u_size.x));\r\n" + //
				"    vec2 base = corner + a_position;\r\n" + //
				"    float height = texture2D(u_heightMap, base / u_size).r;\r\n" + //
				"    float waterHeight = texture2D(u_waterHeightMap, base / u_size).r + u_offsetHeight;\r\n" + //
				"    float value = clamp(waterHeight - height, 0.0, 1.0);\r\n" + //
				"    if (value <= deepLevel) {\r\n" + //
				"      value = max(0.0, value - minDepth) / (deepLevel - minDepth);\r\n" + //
				"      v_color = mix(u_minShallowColor, u_maxShallowColor, value) / 255.0;\r\n" + //
				"    } else {\r\n" + //
				"      value = clamp(value - deepLevel, 0.0, maxDepth - deepLevel) / (maxDepth - deepLevel);\r\n" + //
				"      v_color = mix(u_minDeepColor, u_maxDeepColor, value) / 255.0;\r\n" + //
				"    }\r\n" + //
				"    gl_Position = u_VP * vec4(base * 128.0 + u_offset, waterHeight * 128.0, 1.0);\r\n" + //
				"  } else {\r\n" + //
				"    v_uv = vec2(0.0);\r\n" + //
				"    v_color = vec4(0.0);\r\n" + //
				"    gl_Position = vec4(0.0);\r\n" + //
				"  }\r\n" + //
				"}\r\n" + //
				"";
	}
}
